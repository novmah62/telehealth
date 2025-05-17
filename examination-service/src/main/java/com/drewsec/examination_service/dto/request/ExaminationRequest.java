package com.drewsec.examination_service.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ExaminationRequest(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        LocalDateTime startTime,
        String summary
) {}
