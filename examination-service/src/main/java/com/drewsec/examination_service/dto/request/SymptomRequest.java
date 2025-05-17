package com.drewsec.examination_service.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SymptomRequest(
        UUID examinationId,
        String description,
        Boolean reportedByPatient
) {}
