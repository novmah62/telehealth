package com.drewsec.appointment_service.event.model;

import com.drewsec.appointment_service.entity.Appointment;
import com.drewsec.appointment_service.enumType.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreatedEvent {

    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private LocalDateTime timestamp;

    public static AppointmentCreatedEvent from(Appointment a) {
        return AppointmentCreatedEvent.builder()
                .appointmentId(a.getId())
                .doctorId(a.getDoctorId())
                .patientId(a.getPatientId())
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .status(a.getStatus())
                .timestamp(a.getCreatedAt())
                .build();
    }

}