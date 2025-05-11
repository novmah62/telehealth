package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.dto.request.AppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse bookAppointment(AppointmentRequest request);
    void cancelAppointment(UUID appointmentId, String reason);
    List<AppointmentResponse> listByPatient(UUID patientId);
    List<AppointmentResponse> listNextByDoctor(UUID doctorId, int limit);
}
