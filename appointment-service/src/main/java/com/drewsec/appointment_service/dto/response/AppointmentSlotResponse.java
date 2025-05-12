package com.drewsec.appointment_service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AppointmentSlotResponse(
        UUID slotId,
        UUID availabilityId,
        LocalDateTime slotStartTime,
        LocalDateTime slotEndTime,
        Boolean isBooked
) {}
