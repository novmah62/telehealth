package com.drewsec.examination_service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ExaminationResponse(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status,
        String summary,
        List<SymptomResponse> symptoms,
        List<DiagnosisResponse> diagnoses,
        List<ClinicalTestResponse> clinicalTests,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

