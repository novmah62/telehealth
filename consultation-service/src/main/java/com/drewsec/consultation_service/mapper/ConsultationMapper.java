package com.drewsec.consultation_service.mapper;

import com.drewsec.consultation_service.dto.response.ConsultationResponse;
import com.drewsec.consultation_service.entity.Consultation;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public ConsultationResponse toConsultationResponse(Consultation consultation) {
        return ConsultationResponse.builder()
                .id(consultation.getId())
                .consultantId(consultation.getConsultantId())
                .patientId(consultation.getPatientId())
                .status(consultation.getStatus())
                .build();
    }

}
