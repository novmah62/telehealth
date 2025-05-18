package com.drewsec.examination_service.service.impl;

import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.examination_service.dto.request.ClinicalTestRequest;
import com.drewsec.examination_service.dto.request.ClinicalTestResultRequest;
import com.drewsec.examination_service.dto.response.ClinicalTestResponse;
import com.drewsec.examination_service.entity.ClinicalTest;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.mapper.ClinicalTestMapper;
import com.drewsec.examination_service.repository.ClinicalTestRepository;
import com.drewsec.examination_service.repository.ExaminationRepository;
import com.drewsec.examination_service.service.ClinicalTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicalTestServiceImpl implements ClinicalTestService {

    private final ClinicalTestRepository clinicalTestRepository;
    private final ExaminationRepository examinationRepository;

    @Override
    @Transactional
    public ClinicalTestResponse orderTest(UUID examinationId, ClinicalTestRequest request) {
        Examination exam = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Examination", "examination ID", examinationId.toString()));
        ClinicalTest entity = ClinicalTestMapper.toEntity(request, exam);
        ClinicalTest saved = clinicalTestRepository.save(entity);
        return ClinicalTestMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ClinicalTestResponse updateTest(UUID clinicalTestId, ClinicalTestResultRequest request) {
        ClinicalTest existing = clinicalTestRepository.findById(clinicalTestId)
                .orElseThrow(() -> new ResourceNotFoundException("ClinicalTest", "clinical test ID", clinicalTestId.toString()));

        ClinicalTestMapper.updateEntityFromResult(existing, request);
        ClinicalTest updated = clinicalTestRepository.save(existing);
        return ClinicalTestMapper.toResponse(updated);
    }

    @Override
    public List<ClinicalTestResponse> listByExamination(UUID examinationId) {
        return clinicalTestRepository.findByExaminationId(examinationId).stream()
                .map(ClinicalTestMapper::toResponse)
                .collect(Collectors.toList());
    }
}
