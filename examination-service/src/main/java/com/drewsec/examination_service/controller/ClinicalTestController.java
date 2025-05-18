package com.drewsec.examination_service.controller;

import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.examination_service.dto.request.ClinicalTestRequest;
import com.drewsec.examination_service.dto.request.ClinicalTestResultRequest;
import com.drewsec.examination_service.dto.response.ClinicalTestResponse;
import com.drewsec.examination_service.service.ClinicalTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/clinical-tests")
@RequiredArgsConstructor
public class ClinicalTestController {

    private final ClinicalTestService clinicalTestService;

    @PostMapping("/examinations/{examinationId}")
    public ApiResponse<ClinicalTestResponse> orderTest(
            @PathVariable UUID examinationId,
            @RequestBody ClinicalTestRequest request
    ) {
        ClinicalTestResponse response = clinicalTestService.orderTest(
                examinationId,
                request
        );
        return new ApiResponse<>(
                STATUS_CREATED,
                CLINICAL_TEST_ORDERED,
                response
        );
    }

    @PutMapping("/{clinicalTestId}")
    public ApiResponse<ClinicalTestResponse> updateTest(
            @PathVariable UUID clinicalTestId,
            @RequestBody ClinicalTestResultRequest request
    ) {
        ClinicalTestResponse response = clinicalTestService.updateTest(
                clinicalTestId,
                request
        );
        return new ApiResponse<>(
                STATUS_OK,
                CLINICAL_TEST_UPDATED,
                response
        );
    }

    @GetMapping("/examinations/{examinationId}")
    public ApiResponse<List<ClinicalTestResponse>> listByExamination(
            @PathVariable UUID examinationId
    ) {
        List<ClinicalTestResponse> list = clinicalTestService.listByExamination(
                examinationId
        );
        return new ApiResponse<>(
                STATUS_OK,
                EXAMINATION_TESTS_FETCHED,
                list
        );
    }
}
