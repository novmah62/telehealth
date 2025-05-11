package com.drewsec.appointment_service.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record DoctorAvailabilityResponse(
        UUID id,
        UUID doctorId,
        LocalDate availableDate,
        LocalTime workStart,
        LocalTime workEnd,
        Boolean isAvailable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}