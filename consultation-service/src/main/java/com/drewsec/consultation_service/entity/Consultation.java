package com.drewsec.consultation_service.entity;

import com.drewsec.consultation_service.enumType.ConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "consultations")
public class Consultation {

    @Id
    private String id;
    private String consultantId;
    private String patientId;
    private ConsultationStatus status; // PENDING, ACTIVE, COMPLETED, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
