package com.drewsec.examination_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "symptoms",
        indexes = @Index(name = "idx_symptom_examination", columnList = "examination_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Symptom extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;

    @NotBlank
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull
    @Column(name = "reported_by_patient", nullable = false)
    private Boolean reportedByPatient;

    @Version
    private Long version;
}

