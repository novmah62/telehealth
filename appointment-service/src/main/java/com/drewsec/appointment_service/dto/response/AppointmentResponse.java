package com.drewsec.appointment_service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AppointmentResponse(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status,
        String description,
        UUID slotId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
