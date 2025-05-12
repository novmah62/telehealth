package com.drewsec.appointment_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "doctor_availabilities",
        indexes = @Index(name = "idx_doctor_avail_doctor_date", columnList = "doctor_id,available_date")
)
@Check(constraints = "work_start < work_end")
@Getter
@Setter
@NoArgsConstructor
public class DoctorAvailability extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @NotNull
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @NotNull
    @Column(name = "work_start", nullable = false)
    private LocalTime workStart;

    @NotNull
    @Column(name = "work_end", nullable = false)
    private LocalTime workEnd;

    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

}

