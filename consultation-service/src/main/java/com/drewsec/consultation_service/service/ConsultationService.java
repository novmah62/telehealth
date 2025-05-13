package com.drewsec.consultation_service.service;

import com.drewsec.consultation_service.dto.response.ConsultationResponse;

import java.util.List;
import java.util.UUID;

public interface ConsultationService {

    ConsultationResponse createConsultation(UUID patientId);
    ConsultationResponse acceptConsultation(UUID consultationId, UUID consultantId);
    ConsultationResponse completeConsultation(UUID consultationId, UUID userId);
    List<ConsultationResponse> getConsultationsByPatient(UUID patientId);
    List<ConsultationResponse> getConsultationsByConsultant(UUID consultantId);
    List<ConsultationResponse> getPendingConsultations();

}
