package com.drewsec.prescription_service.service.impl;

import com.drewsec.commons.exception.AccessDeniedException;
import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.prescription_service.dto.request.PrescriptionRequest;
import com.drewsec.prescription_service.dto.response.PrescriptionResponse;
import com.drewsec.prescription_service.entity.DigitalSignature;
import com.drewsec.prescription_service.entity.Prescription;
import com.drewsec.prescription_service.mapper.PrescriptionMapper;
import com.drewsec.prescription_service.repository.PrescriptionRepository;
import com.drewsec.prescription_service.service.PrescriptionService;
import com.drewsec.prescription_service.service.SignatureService;
import com.drewsec.prescription_service.service.SqrcService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;
    private final SignatureService signService;
    private final SqrcService sqrcService;

    @Override
    @Transactional
    public PrescriptionResponse issue(UUID doctorId,PrescriptionRequest request) {
        Prescription entity = repository.findById(request.prescriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Prescription", "Prescription ID", request.prescriptionId().toString()));
        if (doctorId != entity.getDoctorId()) {
            throw new AccessDeniedException("Access denied: You are not the doctor who issued this prescription.");
        }
        byte[] data = entity.getPrescriptionCode().getBytes(StandardCharsets.UTF_8);
        DigitalSignature sig = signService.sign(data);
        String encrypted = sqrcService.generateEncryptedPayload(entity.getPrescriptionCode(), sig);
        String qrBase64 = sqrcService.generateQrBase64(encrypted);
        PrescriptionMapper.updateEntityFromRequest(entity, request,
                new DigitalSignature(
                        sig.getSignatureValueBase64(), sig.getAlgorithm(), sig.getCertificateSerial()),
                qrBase64);
        repository.save(entity);
        return PrescriptionMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> listByDoctor(UUID doctorId, Pageable pageable) {
        return repository.findByDoctorId(doctorId, pageable)
                .map(PrescriptionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> listByPatient(UUID patientId, Pageable pageable) {
        return repository.findByPatientId(patientId, pageable)
                .map(PrescriptionMapper::toResponse);
    }

}
