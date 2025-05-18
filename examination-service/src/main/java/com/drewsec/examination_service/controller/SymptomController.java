package com.drewsec.examination_service.controller;

import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.examination_service.dto.request.SymptomRequest;
import com.drewsec.examination_service.dto.response.SymptomResponse;
import com.drewsec.examination_service.service.SymptomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/examinations/{examinationId}/symptoms")
@RequiredArgsConstructor
public class SymptomController {

    private final SymptomService symptomService;

    @PostMapping
    public ApiResponse<SymptomResponse> addSymptom(
            @PathVariable UUID examinationId,
            @RequestBody SymptomRequest request) {

        SymptomResponse response = symptomService.addSymptom(
                examinationId,
                request
        );
        return new ApiResponse<>(
                STATUS_CREATED,
                SYMPTOM_ADDED,
                response
        );
    }

    @GetMapping
    public ApiResponse<List<SymptomResponse>> listByExamination(
            @PathVariable UUID examinationId) {

        List<SymptomResponse> list = symptomService.listByExamination(
                examinationId
        );
        return new ApiResponse<>(
                STATUS_OK,
                EXAMINATION_SYMPTOMS_FETCHED,
                list
        );
    }
}