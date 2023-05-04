package com.example.pawcare.services.training;

import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.entities.ReportTraining;
import com.example.pawcare.entities.Training;
import com.example.pawcare.entities.Type;
import com.example.pawcare.repositories.IReportTrainingRepository;
import com.example.pawcare.repositories.ITrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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



    public ResponseEntity<String> decreaseAvailablePlaces(Long idTraining) {
        Optional<Training> optionalTraining = trainingRepository.findById(idTraining);
        if (optionalTraining.isPresent()) {
            Training training = optionalTraining.get();
            int availablePlaces = training.getNbrplaces();
            if (availablePlaces > 0) {
                training.setNbrplaces(availablePlaces - 1);
                trainingRepository.save(training);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("No available places");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}
