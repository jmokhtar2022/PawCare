package com.example.pawcare.controllers;

import com.example.pawcare.entities.*;
import com.example.pawcare.repositories.ITrainingRepository;
import com.example.pawcare.services.training.ITrainingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/training")

public class TrainingController {
    @Autowired
    ITrainingServices trainingServices;
ITrainingRepository trainingRepository;
    @PostMapping("/training")
    public Training AddTraining(@RequestBody Training training) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.of("Europe/Paris"));
        LocalDate date = LocalDate.parse(training.getCDate().toString(), formatter);
        training.setCDate(date);
        return trainingServices.AddTraining(training);
    }

    @GetMapping("/training")
    public List<Training> ListTraining()
    {return trainingServices.ListTraining();}

    @GetMapping("/training/{id}")
    public Training RetrieveTrainingById (@PathVariable("id") long idTraining)
    {return trainingServices.RetrieveTrainingById(idTraining);}

    @DeleteMapping("/training/{id}")
    public void DeleteTraining (@PathVariable("id") long idTraining)
    {trainingServices.DeleteTraining(idTraining);}


    @PutMapping("/training/{id}")
    public Training updateTraining(@PathVariable("id") long id, @RequestBody Training training) {
        Training t = trainingServices.RetrieveTrainingById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.of("Europe/Paris"));
        LocalDate date = LocalDate.parse(training.getCDate().toString(), formatter);
        t.setTitle(training.getTitle());
        t.setCDate(date);
        t.setDuration(training.getDuration());
        t.setNbrplaces(training.getNbrplaces());
        t.setType(training.getType());
        t.setPrice(training.getPrice());
        return trainingServices.UpdateTraining(t);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Training>> searchByTitle(@RequestParam String title) {
        List<Training> trainings = trainingRepository.findByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }


    @PostMapping("/report/{id}")
    public ResponseEntity<String> reportTraining(@PathVariable("id") Long id, @RequestBody String Report) {
        Training training = trainingServices.RetrieveTrainingById(id);
        if (training == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training not found");
        }
        trainingServices.reportTraining(training, Report);
        return ResponseEntity.ok("Training reported successfully");
    }

    @GetMapping("/report")
    public List<Training> getReportedTrainings() {
        List<Training> reportedTrainings = trainingServices.RetrieveReportedTrainings();
        return reportedTrainings.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/allreports")
    public List<ReportTraining> getReports() {
        return trainingServices.getReportTrainings();
    }
    @GetMapping("/reports/{idTraining}")
    public ResponseEntity<List<ReportTraining>> getCommentsForAdoption(@PathVariable(value = "idTraining") Long idTraining) {
        try {
            List<ReportTraining> reportTrainings = trainingServices.getReportsByTraining(idTraining);
            return new ResponseEntity<>(reportTrainings, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/statistics")
    public Map<String, Long> getTrainingStatistics() {
        long dogTrainingCount = trainingServices.getDogTrainingCount();
        long catTrainingCount = trainingServices.getCatTrainingCount();
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("dogTrainingCount", dogTrainingCount);
        statistics.put("catTrainingCount", catTrainingCount);
        return statistics;
    }



    @PutMapping("/{idTraining}/booking")
    public ResponseEntity<String> decreaseAvailablePlaces(@PathVariable Long idTraining) {
     return trainingServices.decreaseAvailablePlaces(idTraining);
    }


}







