package com.example.pawcare.repositories;

import com.example.pawcare.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

import java.util.List;

public interface IPetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findByNameLike(String name);
    @Query("SELECT COUNT(e) FROM Pet e WHERE e.createdAt >= :startTime")
    long countByCreatedAtAfter(LocalDateTime startTime);
}
