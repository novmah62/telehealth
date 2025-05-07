package com.drewsec.appointment_service.service;

import com.drewsec.appointment_service.enumType.AppointmentStatus;
import com.drewsec.appointment_service.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityCheckerService {

    private final AppointmentRepository repository;

    public void ensureNoConflict(UUID doctorId, LocalDateTime start, LocalDateTime end) {
        boolean conflict = repository.existsByDoctorIdAndStatusInAndTimeOverlaps(
                doctorId,
                List.of(AppointmentStatus.PENDING, AppointmentStatus.CONFIRMED),
                start,
                end
        );
        if (conflict) {
            throw new IllegalStateException("Time slot conflict for doctor");
        }

    }

}
