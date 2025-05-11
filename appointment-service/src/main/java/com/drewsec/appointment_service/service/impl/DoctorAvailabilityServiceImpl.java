package com.drewsec.appointment_service.service.impl;

import com.drewsec.appointment_service.dto.request.DoctorAvailabilityRequest;
import com.drewsec.appointment_service.dto.response.DoctorAvailabilityResponse;
import com.drewsec.appointment_service.mapper.DoctorAvailabilityMapper;
import com.drewsec.appointment_service.repository.DoctorAvailabilityRepository;
import com.drewsec.appointment_service.service.DoctorAvailabilityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    private final DoctorAvailabilityRepository availabilityRepo;
    private final AppointmentSlotServiceImpl slotService;

    @Override
    @Transactional
    public DoctorAvailabilityResponse createAvailability(DoctorAvailabilityRequest req) {
        if (availabilityRepo.existsByDoctorIdAndAvailableDate(req.doctorId(), req.availableDate())) {
            throw new IllegalArgumentException("Availability already exists for this date");
        }
        var entity = DoctorAvailabilityMapper.toEntity(req);
        var saved = availabilityRepo.save(entity);

        // Generate slots asynchronously
        slotService.generateSlots(saved.getId());

        return DoctorAvailabilityMapper.toResponse(saved);
    }

    @Override
    public List<DoctorAvailabilityResponse> listByDoctorAndDate(UUID doctorId, LocalDate date) {
        return availabilityRepo.findByDoctorIdAndAvailableDate(doctorId, date).stream()
                .map(DoctorAvailabilityMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorAvailabilityResponse> listAvailableWithSlots(UUID doctorId, LocalDate from, LocalDate to) {
        return availabilityRepo.findAvailableWithFreeSlots(doctorId, from, to).stream()
                .map(DoctorAvailabilityMapper::toResponse)
                .collect(Collectors.toList());
    }

}
