package com.example.pawcare.services.hotel;

import com.example.pawcare.entities.Hotel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Ihotel {
    List<Hotel> RetriveALLHotels();

    Hotel addHotel(Hotel hotel);

    Hotel updateHotel(Hotel hotel,Long hotelid);

    Hotel retrieveHotel(Long hotelId);

    void removeHotel(Long hotelId);

}
