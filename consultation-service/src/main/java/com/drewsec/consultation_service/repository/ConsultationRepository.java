package com.drewsec.consultation_service.repository;

import com.drewsec.consultation_service.entity.Consultation;
import com.drewsec.consultation_service.enumType.ConsultationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends MongoRepository<Consultation, String> {

    List<Consultation> findByPatientId(String patientId);
    List<Consultation> findByConsultantId(String consultantId);
    List<Consultation> findByStatus(ConsultationStatus consultationStatus);
    List<Consultation> findByStatusAndCreatedAtBefore(ConsultationStatus consultationStatus, LocalDateTime cutoff);

}
