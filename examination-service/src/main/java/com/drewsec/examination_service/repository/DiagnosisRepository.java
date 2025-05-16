package com.drewsec.examination_service.repository;

import com.drewsec.examination_service.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, UUID> {
    List<Diagnosis> findByExaminationId(UUID examinationId);
}
