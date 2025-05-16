package com.drewsec.examination_service.entity;

import com.drewsec.examination_service.enumType.ExaminationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "examinations",
        indexes = {
                @Index(name = "idx_examination_appointment", columnList = "appointment_id"),
                @Index(name = "idx_examination_patient",     columnList = "patient_id"),
                @Index(name = "idx_examination_doctor",      columnList = "doctor_id"),
                @Index(name = "idx_examination_times",       columnList = "start_time,end_time")
        }
)
@Check(constraints = "start_time < end_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Examination extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "appointment_id", nullable = false, updatable = false)
    private UUID appointmentId;

    @NotNull
    @Column(name = "patient_id", nullable = false, updatable = false)
    private UUID patientId;

    @NotNull
    @Column(name = "doctor_id", nullable = false, updatable = false)
    private UUID doctorId;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExaminationStatus status;

    @Column(length = 2000)
    private String summary;

    @OneToMany(mappedBy = "examination",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Symptom> symptoms = new ArrayList<>();

    @OneToMany(mappedBy = "examination",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Diagnosis> diagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "examination",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ClinicalTest> clinicalTests = new ArrayList<>();

    @Version
    private Long version;

    // Business methods
    public void complete(LocalDateTime completedAt) {
        if (status != ExaminationStatus.IN_PROGRESS) {
            throw new IllegalStateException("Cannot complete when status=" + status);
        }
        this.status = ExaminationStatus.COMPLETED;
        this.endTime = completedAt;
    }
}
