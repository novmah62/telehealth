package com.drewsec.appointment_service.service.impl;

import com.drewsec.appointment_service.dto.request.AppointmentSlotRequest;
import com.drewsec.appointment_service.dto.response.AppointmentSlotResponse;
import com.drewsec.appointment_service.entity.AppointmentSlot;
import com.drewsec.appointment_service.entity.DoctorAvailability;
import com.drewsec.appointment_service.mapper.AppointmentSlotMapper;
import com.drewsec.appointment_service.repository.AppointmentSlotRepository;
import com.drewsec.appointment_service.repository.DoctorAvailabilityRepository;
import com.drewsec.appointment_service.service.AppointmentSlotService;
import com.drewsec.commons.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

    private final AppointmentSlotRepository slotRepo;
    private final DoctorAvailabilityRepository availabilityRepo;

    @Override
    @Transactional
    @Async
    public void generateSlots(UUID availabilityId) {
        DoctorAvailability availability = availabilityRepo.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("DoctorAvailability", "Availability ID", availabilityId.toString()));

        LocalDateTime start = LocalDateTime.of(availability.getAvailableDate(), availability.getWorkStart());
        LocalDateTime end   = LocalDateTime.of(availability.getAvailableDate(), availability.getWorkEnd());
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("workStart must be before workEnd");
        }

        List<AppointmentSlot> slots = new ArrayList<>();
        LocalDateTime cursor = start;
        while (!cursor.plusHours(1).isAfter(end)) {
            // build slot
            AppointmentSlotRequest req = AppointmentSlotRequest.builder()
                    .slotStartTime(cursor)
                    .slotEndTime(cursor.plusHours(1))
                    .build();

            AppointmentSlot slot = AppointmentSlotMapper.toEntity(req, availability);
            slots.add(slot);

            cursor = cursor.plusHours(1);
        }

        List<AppointmentSlot> saved = slotRepo.saveAll(slots);
    }

    @Override
    public List<AppointmentSlotResponse> listFreeSlots(UUID doctorId, LocalDate from, LocalDate to) {
        return slotRepo.findFreeSlotsByDoctorAndDateRange(doctorId, from, to).stream()
                .map(AppointmentSlotMapper::toResponse)
                .collect(Collectors.toList());
    }
}
