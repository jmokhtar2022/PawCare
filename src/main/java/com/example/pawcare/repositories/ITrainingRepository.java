package com.example.pawcare.repositories;

import com.example.pawcare.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingRepository extends JpaRepository<Training,Long> {
}
