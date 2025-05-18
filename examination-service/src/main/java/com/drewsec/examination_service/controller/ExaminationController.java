package com.drewsec.examination_service.controller;

import com.drewsec.commons.definitions.constants.ApiConstants;
import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.examination_service.dto.request.ExaminationRequest;
import com.drewsec.examination_service.dto.response.ExaminationResponse;
import com.drewsec.examination_service.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/examinations")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    @PostMapping
    public ApiResponse<ExaminationResponse> createExamination(@RequestBody ExaminationRequest request) {
        ExaminationResponse response = examinationService.createExamination(request);
        return new ApiResponse<>(
                STATUS_CREATED,
                ApiConstants.EXAMINATION_CREATED,
                response
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ExaminationResponse> getExaminationById(
            @PathVariable("id") UUID examinationId) {

        ExaminationResponse response = examinationService.getExaminationById(
                examinationId
        );
        return new ApiResponse<>(
                STATUS_OK,
                EXAMINATION_FETCHED,
                response
        );
    }

    @GetMapping("/doctor")
    public ApiResponse<List<ExaminationResponse>> listByDoctor(
            @RequestParam UUID doctorId) {

        List<ExaminationResponse> list = examinationService.listByDoctor(
                doctorId
        );
        return new ApiResponse<>(
                STATUS_OK,
                DOCTOR_EXAMINATIONS_FETCHED,
                list
        );
    }

    @GetMapping("/patient")
    public ApiResponse<List<ExaminationResponse>> listByPatient(
            @RequestParam UUID patientId) {

        List<ExaminationResponse> list = examinationService.listByPatient(patientId);
        return new ApiResponse<>(
                STATUS_OK,
                PATIENT_EXAMINATIONS_FETCHED,
                list
        );
    }
}

