package com.drewsec.prescription_service.entity;

import com.drewsec.prescription_service.enumType.PrescriptionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prescriptions",
        indexes = {
                @Index(name = "idx_prescription_doctor", columnList = "doctor_id"),
                @Index(name = "idx_prescription_patient", columnList = "patient_id"),
                @Index(name = "idx_prescription_issued", columnList = "issued_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Prescription extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "prescription_code", nullable = false, unique = true, length = 14)
    private String prescriptionCode;

    @Column(name = "doctor_id", nullable = false, length = 50)
    private UUID doctorId;

    @Column(name = "patient_id", nullable = false, length = 50)
    private UUID patientId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "prescription_items",
            joinColumns = @JoinColumn(name = "prescription_id")
    )
    private List<PrescriptionItem> items;

    @NotNull
    @Column(name = "diagnosis_summary", nullable = false, columnDefinition = "TEXT")
    private String diagnosisSummary;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 1)
    private PrescriptionType type;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt = LocalDateTime.now();

    @Embedded
    private DigitalSignature signature;

    @Lob
    @Column(name = "sqrc_image_base64", nullable = false, columnDefinition = "TEXT")
    private String sqrcImageBase64;

    @Version
    private Long version;

}