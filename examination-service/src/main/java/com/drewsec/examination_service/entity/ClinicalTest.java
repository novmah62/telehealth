package com.drewsec.examination_service.entity;

import com.drewsec.examination_service.enumType.TestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clinical_tests",
        indexes = {
                @Index(name = "idx_test_examination", columnList = "examination_id"),
                @Index(name = "idx_test_status",      columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalTest extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;

    @NotBlank
    @Column(name = "test_name", nullable = false, length = 500)
    private String testName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatus status;

    @Column(name = "result_summary", length = 1000)
    private String resultSummary;

    @Column(name = "result_detail", columnDefinition = "TEXT")
    private String resultDetail;

    @Column(name = "file_url", length = 1000)
    private String fileUrl;

    @NotNull
    @Column(name = "ordered_at", nullable = false, updatable = false)
    private LocalDateTime orderedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Version
    private Long version;
}

