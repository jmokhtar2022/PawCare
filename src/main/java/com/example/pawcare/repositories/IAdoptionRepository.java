package com.example.pawcare.repositories;

import com.example.pawcare.entities.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdoptionRepository extends JpaRepository<Adoption,Long> {
}
