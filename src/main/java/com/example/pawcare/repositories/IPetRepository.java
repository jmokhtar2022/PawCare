package com.example.pawcare.repositories;

import com.example.pawcare.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import java.util.List;

public interface IPetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findByNameLike(String name);
    @Query("SELECT COUNT(e) FROM Pet e WHERE e.createdAt >= :startTime")
    long countByCreatedAtAfter(LocalDateTime startTime);

    @Query("SELECT p FROM Pet p JOIN Appointment a ON p.idPet = a.pet.idPet WHERE a.user.id =:userId")
    List<Pet>GetPetbyuser(@Param("userId") Long idUser);

}
