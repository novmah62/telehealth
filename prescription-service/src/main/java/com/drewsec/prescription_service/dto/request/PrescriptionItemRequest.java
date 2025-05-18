package com.drewsec.prescription_service.dto.request;

import lombok.Builder;

@Builder
public record PrescriptionItemRequest(
        String medicineCode,
        Integer quantity,
        String dosage,
        String note
) {}