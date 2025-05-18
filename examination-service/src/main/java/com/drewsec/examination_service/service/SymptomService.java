package com.drewsec.examination_service.service;

import com.drewsec.examination_service.dto.request.SymptomRequest;
import com.drewsec.examination_service.dto.response.SymptomResponse;

import java.util.List;
import java.util.UUID;

public interface SymptomService {
    SymptomResponse addSymptom(UUID examinationId, SymptomRequest request);
    List<SymptomResponse> listByExamination(UUID examinationId);
}
