package com.example.pawcare.repositories;

import com.example.pawcare.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPetRepository extends JpaRepository<Pet,Long> {
}
