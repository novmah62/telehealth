package com.drewsec.appointment_service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record DiagnosisRequest(

        @NotBlank(message = "Diagnosis must not be blank")
        @Size(max = 500, message = "Diagnosis must be at most 500 characters")
        String diagnosis,

        @Future(message = "Follow-up date must be in the future")
        LocalDateTime followUpDate

) {
}
