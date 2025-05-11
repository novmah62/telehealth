package com.drewsec.appointment_service.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record DoctorAvailabilityRequest(
        UUID doctorId,
        LocalDate availableDate,
        LocalTime workStart,
        LocalTime workEnd,
        Boolean isAvailable
) {}