package com.drewsec.prescription_service.controller;

import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.prescription_service.dto.request.DecryptedPayload;
import com.drewsec.prescription_service.dto.request.PrescriptionRequest;
import com.drewsec.prescription_service.dto.response.PrescriptionResponse;
import com.drewsec.prescription_service.service.PrescriptionService;
import com.drewsec.prescription_service.service.SqrcService;
import com.drewsec.prescription_service.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final VerificationService verificationService;
    private final SqrcService sqrcService;

    @PostMapping
    public ApiResponse<PrescriptionResponse> issuePrescription(
            @RequestParam UUID doctorId,
            @RequestBody PrescriptionRequest request) {

        PrescriptionResponse response = prescriptionService.issue(doctorId, request);
        return new ApiResponse<>(
                STATUS_CREATED,
                PRESCRIPTION_ISSUED,
                response
        );
    }

    @GetMapping("/doctor")
    public ApiResponse<Page<PrescriptionResponse>> listByDoctor(
            @RequestParam UUID doctorId,
            Pageable pageable) {

        Page<PrescriptionResponse> list = prescriptionService.listByDoctor(doctorId, pageable);
        return new ApiResponse<>(
                STATUS_OK,
                DOCTOR_PRESCRIPTIONS_FETCHED,
                list
        );
    }

    @GetMapping("/patient")
    public ApiResponse<Page<PrescriptionResponse>> listByPatient(
            @RequestParam UUID patientId,
            Pageable pageable) {

        Page<PrescriptionResponse> list = prescriptionService.listByPatient(patientId, pageable);
        return new ApiResponse<>(
                STATUS_OK,
                PATIENT_PRESCRIPTIONS_FETCHED,
                list
        );
    }

    @PostMapping("/verify")
    public ApiResponse<PrescriptionResponse> verifyFromQr(@RequestParam String qrBase64) {
        String encryptedPayload = sqrcService.extractEncryptedPayload(qrBase64);
        DecryptedPayload payload = sqrcService.decrypt(encryptedPayload);
        return verificationService.verifyAndFetch(payload)
                .map(data -> new ApiResponse<>(
                        STATUS_OK,
                        PRESCRIPTION_VERIFIED,
                        data))
                .orElseGet(() -> new ApiResponse<>(
                        STATUS_UNAUTHORIZED,
                        PRESCRIPTION_VERIFICATION_FAILED
                ));
    }

}
