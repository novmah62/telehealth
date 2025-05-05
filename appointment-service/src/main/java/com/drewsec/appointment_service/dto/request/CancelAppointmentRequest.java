package com.drewsec.appointment_service.dto.request;

import jakarta.validation.constraints.Size;

public record CancelAppointmentRequest(

        @Size(max = 255, message = "reason can be at most 255 characters")
        String reason

) { }
