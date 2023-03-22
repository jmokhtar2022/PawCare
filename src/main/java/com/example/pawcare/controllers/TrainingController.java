package com.example.pawcare.controllers;

import com.example.pawcare.entities.Training;
import com.example.pawcare.services.training.ITrainingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/training")

public class TrainingController {
    @Autowired
    ITrainingServices trainingServices;

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
    public Training UpdateTraining (@PathVariable("id") long id , @RequestBody Training training)
    {Training t=trainingServices.RetrieveTrainingById(id);
        t.setPrice(training.getPrice());
        t.setDuration(training.getDuration());
        t.setNbrplaces(training.getNbrplaces());

        return trainingServices.UpdateTraining(training);
    }

   /* @GetMapping("/training")
    public List<Training> getTrainingList(@RequestParam(required = false) String search) {
        if (search == null || search.isEmpty()) {
            return trainingServices.ListTraining();
        } else {
            return trainingServices.searchTraining(search);
        }
    }*/

}
