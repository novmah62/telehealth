package com.drewsec.appointment_service.repository;

import com.drewsec.appointment_service.entity.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Find appointments for a doctor within a time window
    List<Appointment> findByDoctorIdAndStartTimeBetween(UUID doctorId, LocalDateTime start, LocalDateTime end);

    // Find all appointments for a patient
    List<Appointment> findByPatientId(UUID patientId);

    // Find appointment by slot
    Optional<Appointment> findBySlotId(UUID slotId);

    // Count overlapping appointments for concurrency check
    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.doctorId = :doctorId " +
            "AND a.startTime < :endTime " +
            "AND a.endTime > :startTime")
    boolean existsOverlappingAppointment(
            @Param("doctorId") UUID doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // Find next upcoming appointment for a doctor
    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctorId = :doctorId " +
            "AND a.startTime > :now " +
            "ORDER BY a.startTime ASC")
    List<Appointment> findNextAppointments(
            @Param("doctorId") UUID doctorId,
            @Param("now") LocalDateTime now,
            Pageable pageable);

}
