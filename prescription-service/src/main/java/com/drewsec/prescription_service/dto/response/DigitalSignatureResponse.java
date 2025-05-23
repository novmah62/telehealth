package com.drewsec.prescription_service.dto.response;

import lombok.Builder;

@Builder
public record DigitalSignatureResponse(
        String signatureValueBase64,
        String algorithm,
        String certificateSerial
) {}
