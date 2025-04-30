package com.drewsec.consultation_service.service;

import com.drewsec.consultation_service.dto.response.ConsultationResponse;

import java.util.List;

public interface ConsultationService {

    ConsultationResponse createConsultation(String patientId);
    ConsultationResponse acceptConsultation(String consultationId, String consultantId);
    ConsultationResponse completeConsultation(String consultationId, String userId);
    List<ConsultationResponse> getConsultationsByPatient(String patientId);
    List<ConsultationResponse> getConsultationsByConsultant(String consultantId);
    List<ConsultationResponse> getPendingConsultations();

}
