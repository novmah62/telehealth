package com.drewsec.prescription_service.service;

import com.drewsec.prescription_service.dto.request.DecryptedPayload;
import com.drewsec.prescription_service.entity.DigitalSignature;

public interface SqrcService {
    String generateEncryptedPayload(String prescriptionCode, DigitalSignature signature);
    String generateQrBase64(String encryptedPayload);
    DecryptedPayload decrypt(String encryptedPayload);
}
