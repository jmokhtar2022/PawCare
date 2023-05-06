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

    List<Appointment> findByPetNameLike(String name);

    @Query("SELECT COUNT(e) FROM Appointment e WHERE e.createdAt >= :startTime")
    long countByCreatedAtAfter(LocalDateTime startTime);

    @Query("select p from Appointment p where p.user.id=:id")
    List<Appointment>GetAppointmentsByClientId(@Param("id")Long id);

    @Query("SELECT a FROM Appointment a JOIN a.user u JOIN u.roles r WHERE u.id = :userId AND r.name = 'ROLE_CLIENT'")
    List<Appointment> getClientAppointmentsByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Appointment a JOIN a.doctor d JOIN d.roles r WHERE d.id = :userId AND r.name = 'ROLE_VETERINARY'")
    List<Appointment> getDoctorAppointmentsByUserId(@Param("userId") Long userId);

    @Query("select a from Appointment a where datediff(a.startDate,current_date)=1")
    List<Appointment> GetListAppointmentsTomail();

    @Query("select a from Appointment a where datediff(a.startDate,current_date)<=31")
    List<Appointment> GetListAppointmentsToPurge();

}