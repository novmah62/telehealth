package com.drewsec.appointment_service.mapper;

import com.drewsec.appointment_service.dto.request.CreateAppointmentRequest;
import com.drewsec.appointment_service.dto.request.UpdateAppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;
import com.drewsec.appointment_service.entity.Appointment;

import java.util.UUID;

public class AppointmentMapper {

    public static Appointment toEntity(CreateAppointmentRequest dto, UUID patientId) {
        Appointment entity = new Appointment();
        entity.setDoctorId(dto.doctorId());
        entity.setPatientId(patientId);
        entity.setStartTime(dto.startTime());
        entity.setEndTime(dto.endTime());
        // status defaults to PENDING
        return entity;
    }

    public static void updateEntity(UpdateAppointmentRequest dto, Appointment entity) {
        if (dto.startTime() != null) {
            entity.setStartTime(dto.startTime());
        }
        if (dto.endTime() != null) {
            entity.setEndTime(dto.endTime());
        }
    }

    public static AppointmentResponse toResponse(Appointment entity) {
        return new AppointmentResponse(
                entity.getId(),
                entity.getDoctorId(),
                entity.getPatientId(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStatus(),
                entity.getReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}
