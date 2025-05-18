package com.drewsec.examination_service.service.impl;

import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.examination_service.dto.request.SymptomRequest;
import com.drewsec.examination_service.dto.response.SymptomResponse;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.entity.Symptom;
import com.drewsec.examination_service.mapper.SymptomMapper;
import com.drewsec.examination_service.repository.ExaminationRepository;
import com.drewsec.examination_service.repository.SymptomRepository;
import com.drewsec.examination_service.service.SymptomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository symptomRepository;
    private final ExaminationRepository examinationRepository;

    @Override
    @Transactional
    public SymptomResponse addSymptom(UUID examinationId, SymptomRequest request) {
        Examination exam = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Examination", "examination ID", examinationId.toString()));
        Symptom entity = SymptomMapper.toEntity(request, exam);
        Symptom saved = symptomRepository.save(entity);
        return SymptomMapper.toResponse(saved);
    }

    @Override
    public List<SymptomResponse> listByExamination(UUID examinationId) {
        return symptomRepository.findByExaminationId(examinationId).stream()
                .map(SymptomMapper::toResponse)
                .collect(Collectors.toList());
    }
}