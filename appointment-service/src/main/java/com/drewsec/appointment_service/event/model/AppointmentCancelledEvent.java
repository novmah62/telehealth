package com.drewsec.appointment_service.event.model;

import com.drewsec.appointment_service.entity.Appointment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCancelledEvent {

    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private String reason;
    private LocalDateTime timestamp;

    public static AppointmentCancelledEvent from(Appointment a) {
        return AppointmentCancelledEvent.builder()
                .appointmentId(a.getId())
                .doctorId(a.getDoctorId())
                .patientId(a.getPatientId())
                .reason(a.getReason())
                .timestamp(a.getUpdatedAt())
                .build();
    }

}
