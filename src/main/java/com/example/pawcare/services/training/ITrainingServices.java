package com.example.pawcare.services.training;

import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.entities.ReportTraining;
import com.example.pawcare.entities.Training;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITrainingServices {

    public Training AddTraining(Training training);
    List<Training> ListTraining ();
    public Training UpdateTraining(Training training);
    public Training RetrieveTrainingById(long idTraining);
    public Training DeleteTraining(long idTraining);
    public void reportTraining(Training training, String message);
    public List<Training> RetrieveReportedTrainings();
    public long getDogTrainingCount();
    public long getCatTrainingCount();
    List<ReportTraining> getReportTrainings();
    public List<ReportTraining> getReportsByTraining(Long idTrainign);
    public ResponseEntity<String> decreaseAvailablePlaces(Long idTraining);



}
