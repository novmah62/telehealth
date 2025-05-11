package com.drewsec.commons.event.appointment;

import java.util.UUID;

public record AppointmentCancelledEvent(
        UUID appointmentId,
        String reason
) {}
