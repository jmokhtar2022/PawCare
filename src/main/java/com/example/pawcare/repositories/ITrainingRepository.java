package com.example.pawcare.repositories;

import com.example.pawcare.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITrainingRepository extends JpaRepository<Training,Long> {

    List<Training> findByTitleContainingIgnoreCase(String title);

}
