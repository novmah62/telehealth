package com.drewsec.consultation_service.dto.response;

import com.drewsec.consultation_service.enumType.ConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResponse {

    private String id;
    private String consultantId;
    private String patientId;
    private ConsultationStatus status;

}
