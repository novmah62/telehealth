package com.drewsec.examination_service.dto.request;

import lombok.Builder;

@Builder
public record ClinicalTestRequest(
        String testName
) {}
