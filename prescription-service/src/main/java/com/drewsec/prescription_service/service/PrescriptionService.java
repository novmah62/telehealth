package com.drewsec.prescription_service.service;

import com.drewsec.prescription_service.dto.request.PrepareRequest;
import com.drewsec.prescription_service.dto.request.PrescriptionRequest;
import com.drewsec.prescription_service.dto.response.PrescriptionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PrescriptionService {
    PrescriptionResponse issue(PrescriptionRequest request);
    Page<PrescriptionResponse> listByDoctor(UUID doctorId, Pageable pageable);
    Page<PrescriptionResponse> listByPatient(UUID patientId, Pageable pageable);
}
