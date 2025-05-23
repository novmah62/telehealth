package com.drewsec.prescription_service.service.impl;

import com.drewsec.prescription_service.dto.request.DecryptedPayload;
import com.drewsec.prescription_service.dto.response.PrescriptionResponse;
import com.drewsec.prescription_service.entity.DigitalSignature;
import com.drewsec.prescription_service.mapper.PrescriptionMapper;
import com.drewsec.prescription_service.repository.PrescriptionRepository;
import com.drewsec.prescription_service.service.SignatureService;
import com.drewsec.prescription_service.service.SqrcService;
import com.drewsec.prescription_service.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final PrescriptionRepository repository;
    private final SignatureService signService;
    private final SqrcService sqrcService;

    @Override
    @Transactional(readOnly = true)
    public Optional<PrescriptionResponse> verifyAndFetch(DecryptedPayload payload) {
        String code = payload.code();
        byte[] data = code.getBytes(StandardCharsets.UTF_8);
        return repository.findByPrescriptionCode(code)
                .filter(entity -> signService.verify(data,
                        new DigitalSignature(
                                payload.sig(),
                                payload.alg(),
                                payload.serial())))
                .map(PrescriptionMapper::toResponse);
    }

}
