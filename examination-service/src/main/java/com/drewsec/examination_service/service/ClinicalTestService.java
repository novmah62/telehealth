package com.drewsec.examination_service.service;

import com.drewsec.examination_service.dto.request.ClinicalTestRequest;
import com.drewsec.examination_service.dto.request.ClinicalTestResultRequest;
import com.drewsec.examination_service.dto.response.ClinicalTestResponse;

import java.util.List;
import java.util.UUID;

public interface ClinicalTestService {
    ClinicalTestResponse orderTest(UUID examinationId, ClinicalTestRequest request);
    ClinicalTestResponse updateTest(UUID clinicalTestId, ClinicalTestResultRequest request);
    List<ClinicalTestResponse> listByExamination(UUID examinationId);
}
