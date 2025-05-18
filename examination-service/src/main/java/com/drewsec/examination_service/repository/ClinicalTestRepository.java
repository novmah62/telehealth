package com.drewsec.examination_service.repository;

import com.drewsec.examination_service.entity.ClinicalTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClinicalTestRepository extends JpaRepository<ClinicalTest, UUID> {
    List<ClinicalTest> findByExaminationId(UUID examinationId);
}
