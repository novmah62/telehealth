package com.drewsec.examination_service.dto.request;

import com.drewsec.examination_service.enumType.TestStatus;
import lombok.Builder;

@Builder
public record ClinicalTestResultRequest(
        TestStatus status,
        String resultSummary,
        String resultDetail,
        String fileUrl
) {}

