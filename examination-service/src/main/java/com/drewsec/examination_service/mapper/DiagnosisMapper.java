package com.drewsec.examination_service.mapper;

import com.drewsec.examination_service.dto.request.DiagnosisRequest;
import com.drewsec.examination_service.dto.response.DiagnosisResponse;
import com.drewsec.examination_service.entity.Diagnosis;
import com.drewsec.examination_service.entity.Examination;

public class DiagnosisMapper {

    public static Diagnosis toEntity(DiagnosisRequest req, Examination examination) {
        Diagnosis entity = new Diagnosis();
        entity.setExamination(examination);
        entity.setDescription(req.description());
        entity.setIcdCode(req.icdCode());
        return entity;
    }

    public static DiagnosisResponse toResponse(Diagnosis entity) {
        return DiagnosisResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .icdCode(entity.getIcdCode())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}