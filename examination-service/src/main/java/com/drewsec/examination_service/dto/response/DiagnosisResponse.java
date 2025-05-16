package com.drewsec.examination_service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record DiagnosisResponse(
        UUID id,
        String description,
        String icdCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

