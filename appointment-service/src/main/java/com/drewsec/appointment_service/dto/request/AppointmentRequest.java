package com.drewsec.appointment_service.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AppointmentRequest(
        UUID doctorId,
        UUID slotId,
        String description
) {}
