package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.dto.request.CreateAppointmentRequest;
import com.drewsec.appointment_service.dto.request.UpdateAppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;
import com.drewsec.appointment_service.entity.Appointment;
import com.drewsec.appointment_service.enumType.AppointmentStatus;
import com.drewsec.appointment_service.event.model.AppointmentCancelledEvent;
import com.drewsec.appointment_service.event.model.AppointmentConfirmedEvent;
import com.drewsec.appointment_service.event.model.AppointmentCreatedEvent;
import com.drewsec.appointment_service.event.model.AppointmentUpdatedEvent;
import com.drewsec.appointment_service.event.publisher.AppointmentEventPublisher;
import com.drewsec.appointment_service.mapper.AppointmentMapper;
import com.drewsec.appointment_service.repository.AppointmentRepository;
import com.drewsec.commons.exception.AccessDeniedException;
import com.drewsec.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AvailabilityCheckerService availabilityChecker;
    private final AppointmentEventPublisher publisher;

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest req, UUID patientId) {
        availabilityChecker.ensureNoConflict(req.doctorId(), req.startTime(), req.endTime());
        Appointment entity = AppointmentMapper.toEntity(req, patientId);
        Appointment saved = repository.save(entity);
        // Publish created event
        publisher.publishCreated(AppointmentCreatedEvent.from(saved));
        return AppointmentMapper.toResponse(saved);
    }

    @Override
    public void confirmAppointment(UUID appointmentId, UUID doctorId) {
        Appointment a = repository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        if (!a.getDoctorId().equals(doctorId)) {
            throw new AccessDeniedException("Doctor not authorized");
        }
        if (a.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only pending appointments can be confirmed");
        }
        a.setStatus(AppointmentStatus.CONFIRMED);
        repository.save(a);
        // Publish confirmed event
        publisher.publishConfirmed(AppointmentConfirmedEvent.from(a));
    }

    @Override
    public void cancelAppointment(UUID appointmentId, UUID userId, String role, String reason) {
        Appointment a = repository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        boolean isDoctor = role.equals("DOCTOR") && a.getDoctorId().equals(userId);
        boolean isPatient = role.equals("PATIENT") && a.getPatientId().equals(userId);
        if (!isDoctor && !isPatient) {
            throw new AccessDeniedException("Not authorized to cancel this appointment");
        }
        if (a.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Completed appointments cannot be cancelled");
        }
        a.setStatus(AppointmentStatus.CANCELLED);
        a.setReason(reason);
        repository.save(a);
        // Publish cancelled event
        publisher.publishCancelled(AppointmentCancelledEvent.from(a));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointments(Optional<UUID> doctorId, Optional<UUID> patientId) {
        List<Appointment> list;
        if (doctorId.isPresent()) {
            list = repository.findByDoctorId(doctorId.get());
        } else if (patientId.isPresent()) {
            list = repository.findByPatientId(patientId.get());
        } else {
            throw new IllegalArgumentException("doctorId or patientId must be provided");
        }
        return list.stream().map(AppointmentMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(UUID id, UUID userId, String role) {
        Appointment a = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        boolean authorized = (role.equals("DOCTOR") && a.getDoctorId().equals(userId)) ||
                (role.equals("PATIENT") && a.getPatientId().equals(userId));
        if (!authorized) {
            throw new AccessDeniedException("Not authorized to view this appointment");
        }
        return AppointmentMapper.toResponse(a);
    }

    @Override
    public AppointmentResponse updateAppointment(UUID id, UpdateAppointmentRequest req, UUID userId, String role) {
        Appointment a = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        // Only patient can update timing before confirmation
        if (!role.equals("PATIENT") || !a.getPatientId().equals(userId)) {
            throw new AccessDeniedException("Not authorized to update this appointment");
        }
        if (a.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only pending appointments can be updated");
        }
        LocalDateTime oldStart = a.getStartTime();
        LocalDateTime oldEnd = a.getEndTime();
        availabilityChecker.ensureNoConflict(a.getDoctorId(), req.startTime(), req.endTime());
        AppointmentMapper.updateEntity(req, a);
        Appointment saved = repository.save(a);
        // Publish updated event with old and new times
        publisher.publishUpdated(AppointmentUpdatedEvent.from(saved, oldStart, oldEnd));
        return AppointmentMapper.toResponse(saved);
    }

}

