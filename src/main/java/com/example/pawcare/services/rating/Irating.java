package com.example.pawcare.services.rating;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Rating;

import java.util.List;

public interface Irating {
    List<Rating> RetriveALLRatings();

    Rating addRating(Rating rating  );

    Rating updateRating(Rating rating);

    Rating retrieveRating(Long ratingId);

    void removeRating(Long ratingId);

    Long countratingsByhotel(Long hotelId);
}
