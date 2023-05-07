package com.example.pawcare.services.rating;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Rating;
import com.example.pawcare.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceRating implements Irating{
    @Autowired
    RatingRepository ratingRepository;

    Rating rating;
    @Override
    public List<Rating> RetriveALLRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating retrieveRating(Long ratingId) {
        return ratingRepository.findById(ratingId).get();
    }

    @Override
    public void removeRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public Long countratingsByhotel(Long hotelId) {
        return ratingRepository.countByHotel(hotelId);
    }


}
