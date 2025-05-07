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
public class AppointmentUpdatedEvent {

    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private LocalDateTime oldStartTime;
    private LocalDateTime oldEndTime;
    private LocalDateTime newStartTime;
    private LocalDateTime newEndTime;
    private LocalDateTime timestamp;

    public static AppointmentUpdatedEvent from(Appointment a, LocalDateTime oldStart, LocalDateTime oldEnd) {
        return AppointmentUpdatedEvent.builder()
                .appointmentId(a.getId())
                .doctorId(a.getDoctorId())
                .patientId(a.getPatientId())
                .oldStartTime(oldStart)
                .oldEndTime(oldEnd)
                .newStartTime(a.getStartTime())
                .newEndTime(a.getEndTime())
                .timestamp(a.getUpdatedAt())
                .build();
    }

}
