package com.drewsec.examination_service.service.impl;

import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.examination_service.dto.request.DiagnosisRequest;
import com.drewsec.examination_service.dto.response.DiagnosisResponse;
import com.drewsec.examination_service.entity.Diagnosis;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.mapper.DiagnosisMapper;
import com.drewsec.examination_service.repository.DiagnosisRepository;
import com.drewsec.examination_service.repository.ExaminationRepository;
import com.drewsec.examination_service.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final ExaminationRepository examinationRepository;

    @Override
    @Transactional
    public DiagnosisResponse addDiagnosis(DiagnosisRequest request) {
        Examination exam = examinationRepository.findById(request.examinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Examination", "examination ID", request.examinationId().toString()));
        Diagnosis entity = DiagnosisMapper.toEntity(request, exam);
        Diagnosis saved = diagnosisRepository.save(entity);
        return DiagnosisMapper.toResponse(saved);
    }

    @Override
    public List<DiagnosisResponse> listByExamination(UUID examinationId) {
        return diagnosisRepository.findByExaminationId(examinationId).stream()
                .map(DiagnosisMapper::toResponse)
                .collect(Collectors.toList());
    }
}