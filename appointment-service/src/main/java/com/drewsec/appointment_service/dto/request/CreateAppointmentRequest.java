package com.drewsec.appointment_service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAppointmentRequest(

        @NotNull(message = "doctorId must not be null")
        UUID doctorId,

        @NotNull(message = "startTime must not be null")
        @Future(message = "startTime must be a future date and time")
        LocalDateTime startTime,

        @NotNull(message = "endTime must not be null")
        @Future(message = "endTime must be a future date and time")
        LocalDateTime endTime

) {}
