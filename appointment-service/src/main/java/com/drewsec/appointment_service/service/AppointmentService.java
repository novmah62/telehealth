package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.dto.request.CreateAppointmentRequest;
import com.drewsec.appointment_service.dto.request.DiagnosisRequest;
import com.drewsec.appointment_service.dto.request.UpdateAppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentService {

    AppointmentResponse createAppointment(CreateAppointmentRequest req, UUID patientId);
    void confirmAppointment(UUID appointmentId, UUID doctorId);
    void cancelAppointment(UUID appointmentId, UUID userId, String role, String reason);
    List<AppointmentResponse> getAppointments(Optional<UUID> doctorId, Optional<UUID> patientId);
    AppointmentResponse getAppointmentById(UUID id, UUID userId, String role);
    AppointmentResponse updateAppointment(UUID id, UpdateAppointmentRequest req, UUID userId, String role);

}
