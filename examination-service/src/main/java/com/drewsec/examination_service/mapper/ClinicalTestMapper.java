package com.drewsec.examination_service.mapper;

import com.drewsec.examination_service.dto.request.ClinicalTestRequest;
import com.drewsec.examination_service.dto.response.ClinicalTestResponse;
import com.drewsec.examination_service.entity.ClinicalTest;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.enumType.TestStatus;

import java.time.LocalDateTime;

public class ClinicalTestMapper {

    public static ClinicalTest toEntity(ClinicalTestRequest req, Examination examination) {
        ClinicalTest entity = new ClinicalTest();
        entity.setExamination(examination);
        entity.setTestName(req.testName());
        entity.setStatus(TestStatus.ORDERED);
        entity.setOrderedAt(LocalDateTime.now());
        return entity;
    }

    public static ClinicalTestResponse toResponse(ClinicalTest entity) {
        return ClinicalTestResponse.builder()
                .id(entity.getId())
                .testName(entity.getTestName())
                .status(entity.getStatus().name())
                .resultSummary(entity.getResultSummary())
                .resultDetail(entity.getResultDetail())
                .fileUrl(entity.getFileUrl())
                .orderedAt(entity.getOrderedAt())
                .completedAt(entity.getCompletedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
