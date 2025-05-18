package com.drewsec.prescription_service.dto.response;

import lombok.Builder;

@Builder
public record PrescriptionItemResponse(
        String medicineCode,
        Integer quantity,
        String dosage,
        String note
) {}
