package com.drewsec.prescription_service.service;

import com.drewsec.prescription_service.dto.request.DecryptedPayload;
import com.drewsec.prescription_service.dto.response.PrescriptionResponse;

import java.util.Optional;

public interface VerificationService {
    Optional<PrescriptionResponse> verifyAndFetch(DecryptedPayload payload);
}
