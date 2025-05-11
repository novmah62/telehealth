package com.drewsec.appointment_service.mapper;

import com.drewsec.appointment_service.dto.request.AppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;
import com.drewsec.appointment_service.entity.Appointment;
import com.drewsec.appointment_service.entity.AppointmentSlot;
import com.drewsec.appointment_service.enumType.AppointmentStatus;
import com.drewsec.commons.event.appointment.AppointmentCancelledEvent;
import com.drewsec.commons.event.appointment.AppointmentCreatedEvent;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentRequest req, AppointmentSlot slot) {
        var entity = new Appointment();
        entity.setPatientId(req.patientId());
        entity.setDoctorId(req.doctorId());
        entity.setSlot(slot);
        entity.setDescription(req.description());
        entity.setStatus(AppointmentStatus.SCHEDULED);
        entity.setStartTime(slot.getSlotStartTime());
        entity.setEndTime(slot.getSlotEndTime());
        return entity;
    }

    public static AppointmentResponse toResponse(Appointment entity) {
        return AppointmentResponse.builder()
                .appointmentId(entity.getId())
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .status(entity.getStatus().name())
                .description(entity.getDescription())
                .slotId(entity.getSlot().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AppointmentCreatedEvent toCreatedEvent(Appointment appointment) {
        return new AppointmentCreatedEvent(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
    }

    public static AppointmentCancelledEvent toCancelledEvent(Appointment appointment, String reason) {
        return new AppointmentCancelledEvent(
                appointment.getId(),
                reason
        );
    }

}
