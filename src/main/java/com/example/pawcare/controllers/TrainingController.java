package com.example.pawcare.controllers;

import com.example.pawcare.entities.Training;
import com.example.pawcare.repositories.ITrainingRepository;
import com.example.pawcare.services.training.ITrainingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/training")

public class TrainingController {
    @Autowired
    ITrainingServices trainingServices;
ITrainingRepository trainingRepository;
    @PostMapping("/training")
    public Training AddTraining(@RequestBody Training training)
    {return trainingServices.AddTraining(training);}

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
    public ResponseEntity<Training> UpdateTraining(@PathVariable("id") long id, @Valid @RequestBody Training training, BindingResult result) {
        // Retrieve the existing training from the database
        Training existingTraining = trainingServices.RetrieveTrainingById(id);
        if (existingTraining == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply data validation rules using annotations
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(training);
        }

        // Update the training object with the new data
        existingTraining.setTitle(training.getTitle());
        existingTraining.setType(training.getType());
        existingTraining.setCDate(training.getCDate());
        existingTraining.setDuration(training.getDuration());
        existingTraining.setNbrplaces(training.getNbrplaces());
        existingTraining.setPrice(training.getPrice());

        // Save the updated training to the database
        Training updatedTraining = trainingServices.UpdateTraining(existingTraining);

        // Return the updated training object in the response body
        return ResponseEntity.ok(updatedTraining);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Training>> searchByTitle(@RequestParam String title) {
        List<Training> trainings = trainingRepository.findByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    /*public Training UpdateTraining (@PathVariable("id") long id , @RequestBody Training training)
    {Training t=trainingServices.RetrieveTrainingById(id);
        t.setCDate(training.getCDate());
        t.setDuration(training.getDuration());
        t.setNbrplaces(training.getNbrplaces());
        t.setPrice(training.getPrice());
        t.setType(training.getType());
        t.setTitle(training.getTitle());

        return trainingServices.UpdateTraining(training);
    }*/

   /* @GetMapping("/training")
    public List<Training> getTrainingList(@RequestParam(required = false) String search) {
        if (search == null || search.isEmpty()) {
            return trainingServices.ListTraining();
        } else {
            return trainingServices.searchTraining(search);
        }
    }*/

}
