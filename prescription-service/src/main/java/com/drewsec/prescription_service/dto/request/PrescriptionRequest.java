package com.drewsec.prescription_service.dto.request;

import com.drewsec.prescription_service.enumType.PrescriptionType;
import lombok.Builder;
import lombok.Singular;

import java.util.List;
import java.util.UUID;

@Builder
public record PrescriptionRequest(
        UUID prescriptionId,
        PrescriptionType type,
        @Singular List<PrescriptionItemRequest> items
) {}
