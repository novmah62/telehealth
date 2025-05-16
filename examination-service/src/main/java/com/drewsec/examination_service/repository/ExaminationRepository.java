package com.drewsec.examination_service.repository;

import com.drewsec.examination_service.entity.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExaminationRepository extends JpaRepository<Examination, UUID> {
    List<Examination> findByDoctorId(UUID doctorId);
    List<Examination> findByPatientId(UUID patientId);
}
