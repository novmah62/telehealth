package com.drewsec.consultation_service.controller;

import com.drewsec.commons.dto.ApiResponse;
import com.drewsec.consultation_service.dto.response.ConsultationResponse;
import com.drewsec.consultation_service.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.drewsec.commons.definitions.constants.ApiConstants.*;

@RestController
@RequestMapping("/api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ApiResponse<ConsultationResponse> createConsultation(
//            @AuthenticatedUserId UUID patientId) {
            @RequestParam UUID patientId) {

            ConsultationResponse response = consultationService.createConsultation(patientId);
        return new ApiResponse<>(STATUS_CREATED, CONSULTATION_CREATED, response);
    }

    @PostMapping("/{id}/accept")
    public ApiResponse<ConsultationResponse> acceptConsultation(
            @PathVariable("id") UUID consultationId,
//            @AuthenticatedUserId UUID consultantId) {
            @RequestParam UUID consultantId) {

        ConsultationResponse response = consultationService.acceptConsultation(consultationId, consultantId);
        return new ApiResponse<>(STATUS_OK, CONSULTATION_ACCEPTED, response);
    }

    @PostMapping("/{id}/complete")
    public ApiResponse<ConsultationResponse> completeConsultation(
            @PathVariable("id") UUID consultationId,
//            @AuthenticatedUserId UUID userId) {
            @RequestParam UUID userId) {

            ConsultationResponse response = consultationService.completeConsultation(consultationId, userId);
        return new ApiResponse<>(STATUS_OK, CONSULTATION_COMPLETED, response);
    }

    @GetMapping("/patient")
    public ApiResponse<List<ConsultationResponse>> getByPatient(
//            @AuthenticatedUserId UUID patientId) {
            @RequestParam UUID patientId) {

        List<ConsultationResponse> list = consultationService.getConsultationsByPatient(patientId);
        return new ApiResponse<>(STATUS_OK, PATIENT_CONSULTATIONS_FETCHED, list);
    }

    @GetMapping("/consultant")
    public ApiResponse<List<ConsultationResponse>> getByConsultant(
//            @AuthenticatedUserId String consultantId) {
            @RequestParam UUID consultantId) {
        List<ConsultationResponse> list = consultationService.getConsultationsByConsultant(consultantId);
        return new ApiResponse<>(STATUS_OK, CONSULTANT_CONSULTATIONS_FETCHED, list);
    }

    @GetMapping("/pending")
    public ApiResponse<List<ConsultationResponse>> getPending() {
        List<ConsultationResponse> list = consultationService.getPendingConsultations();
        return new ApiResponse<>(STATUS_OK, PENDING_CONSULTATIONS_FETCHED, list);
    }

}
