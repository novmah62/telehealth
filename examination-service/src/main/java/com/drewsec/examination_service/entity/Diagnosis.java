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
@Table(name = "diagnoses",
        indexes = @Index(name = "idx_diagnosis_examination", columnList = "examination_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;

    @NotBlank
    @Column(nullable = false, length = 2000)
    private String description;

    @Column(name = "icd_code", length = 10)
    private String icdCode;

    @Version
    private Long version;
}

