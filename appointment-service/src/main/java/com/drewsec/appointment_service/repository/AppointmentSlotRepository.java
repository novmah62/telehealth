package com.drewsec.appointment_service.repository;

import com.drewsec.appointment_service.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID> {

    // List slots by availability and booking status
    List<AppointmentSlot> findByDoctorAvailabilityIdAndIsBooked(UUID availabilityId, Boolean isBooked);

    // Check if a slot exists for a given time interval
    boolean existsByDoctorAvailabilityIdAndSlotStartTimeAndSlotEndTime(
            UUID availabilityId,
            LocalDateTime slotStartTime,
            LocalDateTime slotEndTime);

    // Find an available slot by ID
    Optional<AppointmentSlot> findByIdAndIsBooked(UUID slotId, Boolean isBooked);

    // Custom: find free slots in a date range for a doctor
    @Query("SELECT s FROM AppointmentSlot s " +
            "JOIN s.doctorAvailability da " +
            "WHERE da.doctorId = :doctorId " +
            "AND da.availableDate BETWEEN :fromDate AND :toDate " +
            "AND s.isBooked = false " +
            "ORDER BY da.availableDate, s.slotStartTime")
    List<AppointmentSlot> findFreeSlotsByDoctorAndDateRange(
            @Param("doctorId") UUID doctorId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    // Custom: find booked slots count per day for reporting
    @Query("SELECT da.availableDate, COUNT(s) FROM AppointmentSlot s " +
            "JOIN s.doctorAvailability da " +
            "WHERE da.doctorId = :doctorId " +
            "AND da.availableDate BETWEEN :startDate AND :endDate " +
            "AND s.isBooked = true " +
            "GROUP BY da.availableDate " +
            "ORDER BY da.availableDate")
    List<Object[]> countBookedSlotsPerDay(
            @Param("doctorId") UUID doctorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
