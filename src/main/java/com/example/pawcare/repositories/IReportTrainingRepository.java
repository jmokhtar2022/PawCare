package com.example.pawcare.repositories;

import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.entities.ReportTraining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReportTrainingRepository extends JpaRepository<ReportTraining,Long> {
    List<ReportTraining> findByTrainingIdTraining(long idTraining);
}
