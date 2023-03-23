package com.example.pawcare.services.training;

import com.example.pawcare.entities.Training;

import java.util.List;

public interface ITrainingServices {

    public Training AddTraining(Training training);
    List<Training> ListTraining ();
    public Training UpdateTraining(Training training);
    public Training RetrieveTrainingById(long idTraining);
    public Training DeleteTraining(long idTraining);
    //public List<Training> searchTraining(String keyword);



}
