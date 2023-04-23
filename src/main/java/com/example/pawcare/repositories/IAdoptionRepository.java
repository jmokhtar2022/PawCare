package com.example.pawcare.repositories;

import com.example.pawcare.entities.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdoptionRepository extends JpaRepository<Adoption,Long> {
}
