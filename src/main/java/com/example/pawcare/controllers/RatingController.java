package com.example.pawcare.controllers;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Rating;
import com.example.pawcare.services.rating.Irating;
import com.example.pawcare.services.rating.ServiceRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {



    @Autowired
    ServiceRating serviceRating;


    @PostMapping("/addRating")
    public Rating addRating(@RequestBody Rating rating )
    {
        return serviceRating.addRating(rating);
    }

    @PostMapping("/updateRating")
    public Rating updateRating(@RequestBody Rating rating)
    {
        return serviceRating.updateRating(rating);
    }

    @GetMapping("/findRating/{id}")
    public Rating retrieveRating(@PathVariable("id") Long ratingId)
    {
        return serviceRating.retrieveRating(ratingId);
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Rating> findAllHotels()
    {
        return serviceRating.RetriveALLRatings();
    }

    @DeleteMapping("/deleteRating/{id}")
    public void deleteRating(@PathVariable("id") Long ratingId)
    {
        serviceRating.removeRating(ratingId);
    }



}
