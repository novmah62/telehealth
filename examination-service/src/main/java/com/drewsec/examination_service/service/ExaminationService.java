package com.drewsec.examination_service.service;

import com.drewsec.examination_service.dto.request.ExaminationRequest;
import com.drewsec.examination_service.dto.response.ExaminationResponse;

import java.util.List;
import java.util.UUID;

public interface ExaminationService {
    ExaminationResponse createExamination(ExaminationRequest request);
    ExaminationResponse getExaminationById(UUID examinationId);
    List<ExaminationResponse> listByDoctor(UUID doctorId);
    List<ExaminationResponse> listByPatient(UUID patientId);
}