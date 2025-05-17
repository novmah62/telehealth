package com.drewsec.examination_service.repository;

import com.drewsec.examination_service.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SymptomRepository extends JpaRepository<Symptom, UUID> {
    List<Symptom> findByExaminationId(UUID examinationId);
}
