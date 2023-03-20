package com.example.pawcare.repositories;

import com.example.pawcare.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("SELECT a FROM Appointment a WHERE a.endDate > :startDate AND a.startDate < :endDate")
    List<Appointment> findConflictingAppointments(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
