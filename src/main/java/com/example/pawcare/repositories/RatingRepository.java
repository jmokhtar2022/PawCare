package com.example.pawcare.repositories;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("SELECT AVG (r.ratingValue) FROM Rating r WHERE r.hotel.hotelId =:hotelId")
    long countByHotel(Long hotelId);
}
