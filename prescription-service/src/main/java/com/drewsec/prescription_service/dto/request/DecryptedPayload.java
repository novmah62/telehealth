package com.drewsec.prescription_service.dto.request;

public record DecryptedPayload(
        String code,
        String sig,
        String alg,
        String serial
) {}
