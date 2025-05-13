package com.drewsec.consultation_service.repository;

import com.drewsec.consultation_service.entity.Consultation;
import com.drewsec.consultation_service.enumType.ConsultationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultationRepository extends MongoRepository<Consultation, UUID> {

    List<Consultation> findByPatientId(UUID patientId);
    List<Consultation> findByConsultantId(UUID consultantId);
    List<Consultation> findByStatus(ConsultationStatus consultationStatus);
    List<Consultation> findByStatusAndCreatedAtBefore(ConsultationStatus consultationStatus, LocalDateTime cutoff);

}
