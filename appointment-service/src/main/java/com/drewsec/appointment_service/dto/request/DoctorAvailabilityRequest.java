package com.drewsec.appointment_service.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record DoctorAvailabilityRequest(
        LocalDate availableDate,
        LocalTime workStart,
        LocalTime workEnd,
        Boolean isAvailable
) {}