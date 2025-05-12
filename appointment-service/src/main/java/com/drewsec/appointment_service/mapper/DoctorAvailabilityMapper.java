package com.drewsec.appointment_service.mapper;

import com.drewsec.appointment_service.dto.request.DoctorAvailabilityRequest;
import com.drewsec.appointment_service.dto.response.DoctorAvailabilityResponse;
import com.drewsec.appointment_service.entity.DoctorAvailability;

public class DoctorAvailabilityMapper {

    public static DoctorAvailability toEntity(DoctorAvailabilityRequest req) {
        var entity = new DoctorAvailability();
        entity.setAvailableDate(req.availableDate());
        entity.setWorkStart(req.workStart());
        entity.setWorkEnd(req.workEnd());
        entity.setIsAvailable(req.isAvailable());
        return entity;
    }

    public static DoctorAvailabilityResponse toResponse(DoctorAvailability entity) {
        return DoctorAvailabilityResponse.builder()
                .id(entity.getId())
                .doctorId(entity.getDoctorId())
                .availableDate(entity.getAvailableDate())
                .workStart(entity.getWorkStart())
                .workEnd(entity.getWorkEnd())
                .isAvailable(entity.getIsAvailable())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
