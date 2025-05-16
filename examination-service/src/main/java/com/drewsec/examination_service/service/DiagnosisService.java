package com.drewsec.examination_service.service;

import com.drewsec.examination_service.dto.request.DiagnosisRequest;
import com.drewsec.examination_service.dto.response.DiagnosisResponse;

import java.util.List;
import java.util.UUID;

public interface DiagnosisService {
    DiagnosisResponse addDiagnosis(DiagnosisRequest request);
    List<DiagnosisResponse> listByExamination(UUID examinationId);
}
