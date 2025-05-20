package com.drewsec.prescription_service.dto.response;

public record DecryptedPayload(
        String code,
        String sig,
        String alg,
        String serial
) {}
