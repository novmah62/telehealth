package com.drewsec.examination_service.mapper;

import com.drewsec.examination_service.dto.request.ExaminationRequest;
import com.drewsec.examination_service.dto.response.ExaminationResponse;
import com.drewsec.examination_service.entity.Examination;
import com.drewsec.examination_service.enumType.ExaminationStatus;

import java.util.stream.Collectors;

public class ExaminationMapper {

    public static Examination toEntity(ExaminationRequest req) {
        Examination entity = new Examination();
        entity.setAppointmentId(req.appointmentId());
        entity.setPatientId(req.patientId());
        entity.setDoctorId(req.doctorId());
        entity.setStartTime(req.startTime());
        entity.setStatus(ExaminationStatus.IN_PROGRESS);
        entity.setSummary(req.summary());
        return entity;
    }

    public static ExaminationResponse toResponse(Examination entity) {
        return ExaminationResponse.builder()
                .id(entity.getId())
                .appointmentId(entity.getAppointmentId())
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .status(entity.getStatus().name())
                .summary(entity.getSummary())
                .symptoms(entity.getSymptoms().stream()
                        .map(SymptomMapper::toResponse)
                        .collect(Collectors.toList()))
                .diagnoses(entity.getDiagnoses().stream()
                        .map(DiagnosisMapper::toResponse)
                        .collect(Collectors.toList()))
                .clinicalTests(entity.getClinicalTests().stream()
                        .map(ClinicalTestMapper::toResponse)
                        .collect(Collectors.toList()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
