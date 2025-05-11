package com.drewsec.appointment_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointment_slots",
        indexes = @Index(name = "idx_slot_avail_start_end", columnList = "doctor_availability_id,slot_start_time,slot_end_time")
)
@Check(constraints = "slot_start_time < slot_end_time")
@Getter
@Setter
@NoArgsConstructor
public class AppointmentSlot extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_availability_id", nullable = false)
    private DoctorAvailability doctorAvailability;

    @NotNull
    @Column(name = "slot_start_time", nullable = false)
    private LocalDateTime slotStartTime;

    @NotNull
    @Column(name = "slot_end_time", nullable = false)
    private LocalDateTime slotEndTime;

    @NotNull
    @Column(name = "is_booked", nullable = false)
    private Boolean isBooked;

    @Version
    private Long version;

}
