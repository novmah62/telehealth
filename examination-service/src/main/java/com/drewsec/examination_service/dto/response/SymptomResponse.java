package com.drewsec.examination_service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SymptomResponse(
        UUID id,
        String description,
        Boolean reportedByPatient,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
