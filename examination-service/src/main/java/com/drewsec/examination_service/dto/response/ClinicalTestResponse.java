package com.drewsec.examination_service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ClinicalTestResponse(
        UUID id,
        String testName,
        String status,
        String resultSummary,
        String resultDetail,
        String fileUrl,
        LocalDateTime orderedAt,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
