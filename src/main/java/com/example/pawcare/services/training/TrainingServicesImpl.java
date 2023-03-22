package com.example.pawcare.services.training;

import com.example.pawcare.entities.Training;
import com.example.pawcare.repositories.ITrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServicesImpl implements ITrainingServices {

    @Autowired
    ITrainingRepository trainingRepository;
    private TrainingServicesImpl(){}
    @Override
    public Training AddTraining(Training training) {return trainingRepository.save(training);}
    @Override
    public List<Training> ListTraining()
    {
        return trainingRepository.findAll();
    }
    public Training UpdateTraining(Training training)
    {
        return trainingRepository.save(training);
    }
    public Training RetrieveTrainingById(long idTraining)
    {
        return trainingRepository.findById(idTraining).get();
    }
    public Training DeleteTraining(long idTraining) {trainingRepository.deleteById(idTraining); return null;}

    /*public List<Training> searchTraining(String keyword) {
        List<Training> allTraining = ListTraining();
        List<Training> filteredTraining = new ArrayList<>();
        for (Training training : allTraining) {
            if (training.getTile().contains(keyword)) {
                filteredTraining.add(training);
            }
        }
        return filteredTraining;
    }*/


}
