package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.dto.request.DoctorAvailabilityRequest;
import com.drewsec.appointment_service.dto.response.DoctorAvailabilityResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DoctorAvailabilityService {

    DoctorAvailabilityResponse createAvailability(UUID doctorId, DoctorAvailabilityRequest request);
    List<DoctorAvailabilityResponse> listByDoctorAndDate(UUID doctorId, LocalDate date);
    List<DoctorAvailabilityResponse> listAvailable(UUID doctorId, LocalDate from, LocalDate to);

}
