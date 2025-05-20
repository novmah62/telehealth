package com.drewsec.prescription_service.service;

import com.drewsec.prescription_service.entity.DigitalSignature;

public interface SignatureService {
    DigitalSignature sign(byte[] data);
    boolean verify(byte[] data, DigitalSignature signature);
}
