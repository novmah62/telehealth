package com.drewsec.appointment_service.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentSlotRequest(
        @NotNull(message = "Start time is required")
        @FutureOrPresent(message = "Start time must be now or in the future")
        LocalDateTime slotStartTime,
        @NotNull(message = "End time is required")
        @Future(message = "End time must be in the future")
        LocalDateTime slotEndTime
) {
    @AssertTrue(message = "End time must be after start time")
    public boolean isEndAfterStart() {
        return slotEndTime.isAfter(slotStartTime);
    }
}
