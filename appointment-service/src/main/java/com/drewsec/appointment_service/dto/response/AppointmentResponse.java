package com.drewsec.appointment_service.dto.response;

import com.drewsec.appointment_service.enumType.AppointmentStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(

        UUID id,
        UUID doctorId,
        UUID patientId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status,
        String reason,
        Instant createdAt,
        Instant updatedAt

) { }
