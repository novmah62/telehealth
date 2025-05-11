package com.drewsec.appointment_service.service.impl;

import com.drewsec.appointment_service.dto.request.AppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;
import com.drewsec.appointment_service.entity.Appointment;
import com.drewsec.appointment_service.entity.AppointmentSlot;
import com.drewsec.appointment_service.enumType.AppointmentStatus;
import com.drewsec.appointment_service.event.publisher.AppointmentEventPublisher;
import com.drewsec.appointment_service.mapper.AppointmentMapper;
import com.drewsec.appointment_service.repository.AppointmentRepository;
import com.drewsec.appointment_service.repository.AppointmentSlotRepository;
import com.drewsec.appointment_service.service.AppointmentService;
import com.drewsec.commons.exception.ConflictException;
import com.drewsec.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentSlotRepository slotRepository;
    private final AppointmentEventPublisher appointmentEventPublisher;

    @Override
    @Transactional
    public AppointmentResponse bookAppointment(AppointmentRequest request) {
        // Validate slot exists
        AppointmentSlot slot = slotRepository.findById(request.slotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot", "slot ID", request.slotId().toString()));

        LocalDateTime start = slot.getSlotStartTime();
        LocalDateTime end = slot.getSlotEndTime();

        // Check overlapping
        boolean overlaps = appointmentRepository.existsOverlappingAppointment(
                request.doctorId(), start, end);
        if (overlaps) {
            throw new ConflictException("Doctor already has an appointment in this time window");
        }

        // Ensure slot not already booked
        if (appointmentRepository.findBySlotId(slot.getId()).isPresent()) {
            throw new ConflictException("Slot already booked: " + slot.getId());
        }

        // Map request to entity
        Appointment entity = AppointmentMapper.toEntity(request, slot);

        // Persist
        Appointment saved = appointmentRepository.save(entity);

        // Publish event
        appointmentEventPublisher.publishCreated(
                AppointmentMapper.toCreatedEvent(saved)
        );

        return AppointmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void cancelAppointment(UUID appointmentId, String reason) {
        Appointment appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "Appointment ID", appointmentId.toString()));
        if (appt.getStatus() == AppointmentStatus.CANCELED) {
            return; // idempotent
        }
        appt.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appt);

        // Publish event
        appointmentEventPublisher.publishCancelled(
                AppointmentMapper.toCancelledEvent(appt, reason)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> listByPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(AppointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> listNextByDoctor(UUID doctorId, int limit) {
        List<Appointment> upcoming = appointmentRepository.findNextAppointments(
                doctorId, LocalDateTime.now(), PageRequest.of(0, limit)
        );
        return upcoming.stream()
                .map(AppointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

}
