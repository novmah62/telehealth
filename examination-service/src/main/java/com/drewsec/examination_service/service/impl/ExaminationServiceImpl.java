package com.drewsec.examination_service.service.impl;

import com.drewsec.commons.exception.ResourceNotFoundException;
import com.drewsec.examination_service.dto.request.ExaminationRequest;
import com.drewsec.examination_service.dto.response.ExaminationResponse;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.mapper.ExaminationMapper;
import com.drewsec.examination_service.repository.ExaminationRepository;
import com.drewsec.examination_service.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository examinationRepository;

    @Override
    @Transactional
    public ExaminationResponse createExamination(ExaminationRequest request) {
        Examination entity = ExaminationMapper.toEntity(request);
        Examination saved = examinationRepository.save(entity);
        return ExaminationMapper.toResponse(saved);
    }

    @Override
    public ExaminationResponse getExaminationById(UUID examinationId) {
        Examination entity = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Examination", "examination ID", examinationId.toString()));
        return ExaminationMapper.toResponse(entity);
    }

    @Override
    public List<ExaminationResponse> listByDoctor(UUID doctorId) {
        return examinationRepository.findByDoctorId(doctorId).stream()
                .map(ExaminationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExaminationResponse> listByPatient(UUID patientId) {
        return examinationRepository.findByPatientId(patientId).stream()
                .map(ExaminationMapper::toResponse)
                .collect(Collectors.toList());
    }

}
