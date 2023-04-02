package com.example.pawcare.controllers;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.services.hotel.Ihotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    Ihotel ihotel;

    @PostMapping("/addHotel")
    public Hotel addHotel(@RequestBody Hotel hotel )
    {
        return ihotel.addHotel(hotel);
    }

    @PostMapping("/updateHotel")
    public Hotel updateHotel(@RequestBody Hotel hotel)
    {
        return ihotel.updateHotel(hotel);
    }

    @GetMapping("/findHotel/{id}")
    public Hotel retrieveHotel(@PathVariable("id") Long hotelId)
    {
        return ihotel.retrieveHotel(hotelId);
    }

    @GetMapping("/all")
    public List<Hotel> findAllHotels()
    {
        return ihotel.RetriveALLHotels();
    }

    @DeleteMapping("/deleteHotel/{id}")
    public void deleteHotel(@PathVariable("id") Long hotelId)
    {
        ihotel.removeHotel(hotelId);
    }
}
