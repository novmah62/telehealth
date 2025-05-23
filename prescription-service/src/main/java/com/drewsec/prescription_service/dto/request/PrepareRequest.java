package com.drewsec.prescription_service.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PrepareRequest(
        UUID doctorId,
        UUID patientId,
        String diagnosisSummary
) {}