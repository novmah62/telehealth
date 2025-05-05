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
public class AppointmentConfirmedEvent {

    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private LocalDateTime timestamp;

    public static AppointmentConfirmedEvent from(Appointment a) {
        return AppointmentConfirmedEvent.builder()
                .appointmentId(a.getId())
                .doctorId(a.getDoctorId())
                .patientId(a.getPatientId())
                .timestamp(a.getUpdatedAt())
                .build();
    }

}
