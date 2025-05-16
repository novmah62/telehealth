package com.drewsec.examination_service.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DiagnosisRequest(
        UUID examinationId,
        String description,
        String icdCode
) {}

