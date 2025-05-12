package com.drewsec.appointment_service.controller;

import com.drewsec.appointment_service.dto.request.DoctorAvailabilityRequest;
import com.drewsec.appointment_service.dto.response.DoctorAvailabilityResponse;
import com.drewsec.appointment_service.service.DoctorAvailabilityService;
import com.drewsec.commons.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/availabilities")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService availabilityService;

    @PostMapping
    public ApiResponse<DoctorAvailabilityResponse> createAvailability(
            @RequestParam UUID doctorId,
            @RequestBody DoctorAvailabilityRequest request) {

        DoctorAvailabilityResponse response = availabilityService.createAvailability(
                doctorId, request
        );
        return new ApiResponse<>(
                STATUS_CREATED,
                AVAILABILITY_CREATED,
                response
        );
    }

    @GetMapping
    public ApiResponse<List<DoctorAvailabilityResponse>> listByDoctorAndDate(
            @RequestParam UUID doctorId,
            @RequestParam LocalDate date) {

        List<DoctorAvailabilityResponse> list = availabilityService.listByDoctorAndDate(
                doctorId, date
        );
        return new ApiResponse<>(
                STATUS_OK,
                DOCTOR_AVAILABILITIES_FETCHED,
                list
        );
    }

    @GetMapping("/with-slots")
    public ApiResponse<List<DoctorAvailabilityResponse>> listAvailableWithSlots(
            @RequestParam UUID doctorId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

        List<DoctorAvailabilityResponse> list = availabilityService.listAvailable(
                doctorId, from, to
        );
        return new ApiResponse<>(
                STATUS_OK,
                AVAILABLE_WITH_SLOTS_FETCHED,
                list
        );
    }
}
