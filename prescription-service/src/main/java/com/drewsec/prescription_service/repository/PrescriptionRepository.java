package com.drewsec.prescription_service.repository;

import com.drewsec.prescription_service.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    Optional<Prescription> findByPrescriptionCode(String code);
    Page<Prescription> findByPatientId(UUID patientId, Pageable pageable);
    Page<Prescription> findByDoctorId(UUID doctorId, Pageable pageable);
    Page<Prescription> findByIssuedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    boolean existsByPrescriptionCode(String code);

}
