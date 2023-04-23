package com.example.pawcare.services.training;

import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.entities.ReportTraining;
import com.example.pawcare.entities.Training;
import com.example.pawcare.entities.Type;
import com.example.pawcare.repositories.IReportTrainingRepository;
import com.example.pawcare.repositories.ITrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServicesImpl implements ITrainingServices {

    @Autowired
    ITrainingRepository trainingRepository;
    @Autowired
    IReportTrainingRepository reportTrainingRepository;
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

    public void reportTraining(Training training, String message) {
        ReportTraining report = new ReportTraining();
        report.setMessage(message);
        report.setTraining(training);
        training.getReports().add(report);
        trainingRepository.save(training);
    }

    public List<Training> RetrieveReportedTrainings() {
        return trainingRepository.findByReportsIsNotNull();
    }

    @Override
    public List<ReportTraining> getReportTrainings() {
        return reportTrainingRepository.findAll();
    }

    @Override
    public List<ReportTraining> getReportsByTraining(Long idTraining) {
        return reportTrainingRepository.findByTrainingIdTraining(idTraining);
    }

    public long getDogTrainingCount() {
        return trainingRepository.countByType(Type.Dog);
    }

    public long getCatTrainingCount() {
        return trainingRepository.countByType(Type.Cat);
    }



}
