package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.dto.response.AppointmentSlotResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentSlotService {

    void generateSlots(UUID availabilityId);
    List<AppointmentSlotResponse> listFreeSlots(UUID doctorId, LocalDate from, LocalDate to);

}
