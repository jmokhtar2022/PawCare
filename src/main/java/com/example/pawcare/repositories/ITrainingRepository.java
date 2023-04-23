package com.example.pawcare.repositories;

import com.example.pawcare.entities.Training;
import com.example.pawcare.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrainingRepository extends JpaRepository<Training,Long> {

    List<Training> findByTitleContainingIgnoreCase(String title);

    List<Training> findByReportsIsNotNull();

    long countByType(Type type);



}
