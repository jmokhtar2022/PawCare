package com.example.pawcare.services.training;

import com.example.pawcare.entities.*;
import com.example.pawcare.repositories.IAppointmentRepository;
import com.example.pawcare.repositories.IPetRepository;
import com.example.pawcare.repositories.IReportTrainingRepository;
import com.example.pawcare.repositories.ITrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServicesImpl implements ITrainingServices {

    @Autowired
    ITrainingRepository trainingRepository;
    @Autowired
    IReportTrainingRepository reportTrainingRepository;

    @Autowired
    IPetRepository petRepository;

    @Autowired
    IAppointmentRepository appointmentRepository;

     TrainingServicesImpl(){}
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

    public Training DeleteTraining(long idTraining) {
        Training training = trainingRepository.findById(idTraining)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));

        // remove the relationship between the training and the pets
        training.getPets().forEach(pet -> pet.getTrainings().remove(training));

        // delete the training
        trainingRepository.delete(training);
        return training;

        }

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



    public Training decreaseAvailablePlaces(Long idTraining,Long idPet) {
        Training training=trainingRepository.findById(idTraining).get();
        Pet pet= petRepository.findById(idPet).get();
        pet.getTrainings().add(training);
        int availablePlaces = training.getNbrplaces();
        training.setNbrplaces(availablePlaces - 1);
        return trainingRepository.save(training);
    }





}
