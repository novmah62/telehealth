package com.drewsec.examination_service.controller;

import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.examination_service.dto.request.DiagnosisRequest;
import com.drewsec.examination_service.dto.response.DiagnosisResponse;
import com.drewsec.examination_service.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/examinations/{examinationId}/diagnoses")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @PostMapping
    public ApiResponse<DiagnosisResponse> addDiagnosis(
            @PathVariable UUID examinationId,
            @RequestBody DiagnosisRequest request) {

        DiagnosisResponse response = diagnosisService.addDiagnosis(
                examinationId,
                request
        );
        return new ApiResponse<>(
                STATUS_CREATED,
                DIAGNOSIS_ADDED,
                response
        );
    }

    @GetMapping
    public ApiResponse<List<DiagnosisResponse>> listByExamination(
            @PathVariable UUID examinationId) {

        List<DiagnosisResponse> list = diagnosisService.listByExamination(
                examinationId
        );
        return new ApiResponse<>(
                STATUS_OK,
                EXAMINATION_DIAGNOSES_FETCHED,
                list
        );
    }
}

