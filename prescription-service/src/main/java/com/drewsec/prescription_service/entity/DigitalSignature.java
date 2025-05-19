package com.drewsec.prescription_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DigitalSignature {

    @Lob
    @Column(name = "signature_value", nullable = false, columnDefinition = "TEXT")
    private String signatureValueBase64;

    @Column(name = "algorithm", nullable = false, length = 50)
    private String algorithm;

    @Column(name = "certificate_serial", nullable = false, length = 100)
    private String certificateSerial;

}
