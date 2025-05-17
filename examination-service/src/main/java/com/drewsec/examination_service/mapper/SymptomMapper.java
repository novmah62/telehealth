package com.drewsec.examination_service.mapper;

import com.drewsec.examination_service.dto.request.SymptomRequest;
import com.drewsec.examination_service.dto.response.SymptomResponse;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.entity.Symptom;

public class SymptomMapper {

    public static Symptom toEntity(SymptomRequest req, Examination examination) {
        Symptom entity = new Symptom();
        entity.setExamination(examination);
        entity.setDescription(req.description());
        entity.setReportedByPatient(req.reportedByPatient());
        return entity;
    }

    public static SymptomResponse toResponse(Symptom entity) {
        return SymptomResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .reportedByPatient(entity.getReportedByPatient())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
