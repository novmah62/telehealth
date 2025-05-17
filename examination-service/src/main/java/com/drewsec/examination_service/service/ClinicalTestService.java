package com.drewsec.examination_service.service;

import com.drewsec.examination_service.dto.request.ClinicalTestRequest;
import com.drewsec.examination_service.dto.response.ClinicalTestResponse;

import java.util.List;
import java.util.UUID;

public interface ClinicalTestService {
    ClinicalTestResponse orderTest(ClinicalTestRequest request);
    ClinicalTestResponse updateTest(ClinicalTestRequest request);
    List<ClinicalTestResponse> listByExamination(UUID examinationId);
}
