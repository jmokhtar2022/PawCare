package com.example.pawcare.repositories.auth;

import com.example.pawcare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u.username, u.email, r.name FROM User u JOIN u.roles r")
    List<Object[]> GetAllUsers();

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_VETERINARY' AND u.id NOT IN " +
            "(SELECT a.doctor.id FROM Appointment a WHERE a.startDate < :endDate AND a.endDate > :startDate)")
    List<User> findAvailableDoctors(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select a.doctor from Appointment a where a.idAppointment=:id")
    User GetDoctorByAptId(Long id);

    @Query("select u from User u join u.roles r where r.name= 'ROLE_VETERINARY'")
    List<User>GetAllDoctors();
}
