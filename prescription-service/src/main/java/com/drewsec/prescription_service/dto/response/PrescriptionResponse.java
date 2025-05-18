package com.drewsec.prescription_service.dto.response;

import com.drewsec.prescription_service.enumType.PrescriptionType;
import lombok.Builder;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record PrescriptionResponse(
        UUID id,
        String prescriptionCode,
        UUID doctorId,
        UUID patientId,
        @Singular List<PrescriptionItemResponse> items,
        String diagnosisSummary,
        PrescriptionType type,
        LocalDateTime issuedAt,
        DigitalSignatureResponse signature,
        String sqrcImageBase64,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long version
) {}