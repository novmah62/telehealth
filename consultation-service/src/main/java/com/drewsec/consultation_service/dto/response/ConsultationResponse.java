package com.drewsec.consultation_service.dto.response;

import com.drewsec.consultation_service.enumType.ConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResponse {

    private UUID id;
    private UUID consultantId;
    private UUID patientId;
    private ConsultationStatus status;

}
