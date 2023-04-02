package com.example.pawcare.services.hotel;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceHotel implements Ihotel {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> RetriveALLHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel retrieveHotel(Long hotelId) {
        return hotelRepository.findById(hotelId).get();
    }

    @Override
    public void removeHotel(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }
}
