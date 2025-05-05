package com.drewsec.appointment_service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateAppointmentRequest(

        @NotNull(message = "startTime must not be null")
        @Future(message = "startTime must be in the future")
        LocalDateTime startTime,

        @NotNull(message = "endTime must not be null")
        @Future(message = "endTime must be in the future")
        LocalDateTime endTime

) { }
