package com.drewsec.appointment_service.mapper;

import com.drewsec.appointment_service.dto.request.AppointmentSlotRequest;
import com.drewsec.appointment_service.dto.response.AppointmentSlotResponse;
import com.drewsec.appointment_service.entity.AppointmentSlot;
import com.drewsec.appointment_service.entity.DoctorAvailability;

public class AppointmentSlotMapper {

    public static AppointmentSlot toEntity(AppointmentSlotRequest req, DoctorAvailability availability) {
        var entity = new AppointmentSlot();
        entity.setDoctorAvailability(availability);
        entity.setSlotStartTime(req.slotStartTime());
        entity.setSlotEndTime(req.slotEndTime());
        entity.setIsBooked(false);
        return entity;
    }

    public static AppointmentSlotResponse toResponse(AppointmentSlot entity) {
        return AppointmentSlotResponse.builder()
                .slotId(entity.getId())
                .availabilityId(entity.getDoctorAvailability().getId())
                .slotStartTime(entity.getSlotStartTime())
                .slotEndTime(entity.getSlotEndTime())
                .isBooked(entity.getIsBooked())
                .build();
    }

}
