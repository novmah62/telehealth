package com.drewsec.examination_service.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ClinicalTestRequest(
        UUID examinationId,
        String testName
) {}
