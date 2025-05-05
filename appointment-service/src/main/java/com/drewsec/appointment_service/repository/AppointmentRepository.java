package com.drewsec.appointment_service.repository;

import com.drewsec.appointment_service.entity.Appointment;
import com.drewsec.appointment_service.enumType.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDoctorId(UUID doctorId);
    List<Appointment> findByPatientId(UUID patientId);

    @Query("SELECT CASE WHEN COUNT(a)>0 THEN true ELSE false END FROM Appointment a " +
            "WHERE a.doctorId = :doctorId " +
            "AND a.status IN :statuses " +
            "AND ((a.startTime < :end AND a.endTime > :start))")
    boolean existsByDoctorIdAndStatusInAndTimeOverlaps(
            @Param("doctorId") UUID doctorId,
            @Param("statuses") List<AppointmentStatus> statuses,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
