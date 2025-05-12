package com.drewsec.appointment_service.repository;

import com.drewsec.appointment_service.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, UUID> {

    // Find availabilities for a doctor on a specific date
    List<DoctorAvailability> findByDoctorIdAndAvailableDate(UUID doctorId, LocalDate date);

    // Check if availability exists
    boolean existsByDoctorIdAndAvailableDate(UUID doctorId, LocalDate date);

    // Custom: find date ranges where doctor is available and has free slots
    @Query("SELECT da FROM DoctorAvailability da " +
            "LEFT JOIN AppointmentSlot s ON s.doctorAvailability = da AND s.isBooked = false " +
            "WHERE da.doctorId = :doctorId " +
            "AND da.availableDate BETWEEN :fromDate AND :toDate " +
            "AND s.id IS NOT NULL")
    List<DoctorAvailability> findAvailableWithFreeSlots(
            @Param("doctorId") UUID doctorId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

}

