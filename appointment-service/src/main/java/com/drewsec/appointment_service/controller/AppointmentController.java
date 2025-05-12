package com.drewsec.appointment_service.controller;

import com.drewsec.appointment_service.dto.request.AppointmentRequest;
import com.drewsec.appointment_service.dto.response.AppointmentResponse;
import com.drewsec.appointment_service.service.AppointmentService;
import com.drewsec.commons.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ApiResponse<AppointmentResponse> bookAppointment(
//            @AuthenticatedUserId String patientId,
            @RequestParam String patientId,
            @RequestBody AppointmentRequest request) {

        AppointmentResponse response = appointmentService.bookAppointment(
                UUID.fromString(patientId),
                request
        );
        return new ApiResponse<>(
                STATUS_CREATED,
                APPOINTMENT_BOOKED,
                response
        );
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<Void> cancelAppointment(
            @PathVariable("id") String appointmentId,
//            @AuthenticatedUserId String doctorId,
            @RequestParam String doctorId,
            @RequestParam String reason) {

        appointmentService.cancelAppointment(
                UUID.fromString(doctorId),
                UUID.fromString(appointmentId),
                reason
        );
        return new ApiResponse<>(
                STATUS_OK,
                APPOINTMENT_CANCELLED
        );
    }

    @GetMapping("/patient")
    public ApiResponse<List<AppointmentResponse>> listByPatient(
//            @AuthenticatedUserId String patientId) {
            @RequestParam String patientId) {

        List<AppointmentResponse> list = appointmentService.listByPatient(
                UUID.fromString(patientId)
        );
        return new ApiResponse<>(
                STATUS_OK,
                PATIENT_APPOINTMENTS_FETCHED,
                list
        );
    }

    @GetMapping("/doctor/next")
    public ApiResponse<List<AppointmentResponse>> listNextByDoctor(
//            @AuthenticatedUserId String doctorId,
            @RequestParam String doctorId,
            @RequestParam int limit) {

        List<AppointmentResponse> list = appointmentService.listNextByDoctor(
                UUID.fromString(doctorId),
                limit
        );
        return new ApiResponse<>(
                STATUS_OK,
                DOCTOR_NEXT_APPOINTMENTS_FETCHED,
                list
        );
    }
}
