package com.drewsec.appointment_service.controller;

import com.drewsec.appointment_service.dto.response.AppointmentSlotResponse;
import com.drewsec.appointment_service.service.AppointmentSlotService;
import com.drewsec.commons.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/slots")
@RequiredArgsConstructor
public class AppointmentSlotController {

    private final AppointmentSlotService slotService;

    @GetMapping("/{slotId}")
    public ApiResponse<AppointmentSlotResponse> getSlotById(
            @PathVariable UUID slotId) {

        AppointmentSlotResponse response = slotService.getSlotById(slotId);
        return new ApiResponse<>(
                STATUS_OK,
                SLOT_FETCHED,
                response
        );
    }

    @GetMapping("/free")
    public ApiResponse<List<AppointmentSlotResponse>> listFreeSlotsByDoctor(
            @RequestParam UUID doctorId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

        List<AppointmentSlotResponse> list = slotService.listFreeSlotsByDoctor(
                doctorId, from, to
        );
        return new ApiResponse<>(
                STATUS_OK,
                FREE_SLOTS_FETCHED,
                list
        );
    }

    @GetMapping("/availability")
    public ApiResponse<List<AppointmentSlotResponse>> listSlotsByAvailability(
            @RequestParam UUID availabilityId,
            @RequestParam Boolean isBooked) {

        List<AppointmentSlotResponse> list = slotService.listSlotsByAvailability(
                availabilityId, isBooked
        );
        return new ApiResponse<>(
                STATUS_OK,
                SLOTS_BY_AVAILABILITY_FETCHED,
                list
        );
    }

    @GetMapping("/booked/count")
    public ApiResponse<Map<LocalDate, Long>> countBookedSlotsPerDay(
            @RequestParam UUID doctorId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        Map<LocalDate, Long> counts = slotService.countBookedSlotsPerDay(
                doctorId, startDate, endDate
        );
        return new ApiResponse<>(
                STATUS_OK,
                BOOKED_SLOTS_COUNT_FETCHED,
                counts
        );
    }
}
