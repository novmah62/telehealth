package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.dto.response.AppointmentSlotResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointmentSlotService {

    void generateSlots(UUID availabilityId);
    AppointmentSlotResponse getSlotById(UUID slotId);
    List<AppointmentSlotResponse> listFreeSlotsByDoctor(UUID doctorId, LocalDate from, LocalDate to);
    List<AppointmentSlotResponse> listSlotsByAvailability(UUID availabilityId, Boolean isBooked);
    Map<LocalDate, Long> countBookedSlotsPerDay(UUID doctorId, LocalDate startDate, LocalDate endDate);

}
