package com.example.pawcare.controllers;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Rating;
import com.example.pawcare.services.hotel.Ihotel;
import com.example.pawcare.services.hotel.ResponseMessage;
import com.example.pawcare.services.hotel.ServiceHotel;
import com.example.pawcare.services.rating.ServiceRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    Ihotel ihotel;

    @Autowired
    ServiceHotel serviceHotel;

    @Autowired
    ServiceRating serviceRating;

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
        Long r=serviceRating.countratingsByhotel(hotelId);
        return ihotel.retrieveHotel(hotelId);
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Hotel> findAllHotels()
    {
        return ihotel.RetriveALLHotels();
    }

    @DeleteMapping("/deleteHotel/{id}")
    public void deleteHotel(@PathVariable("id") Long hotelId)
    {
        ihotel.removeHotel(hotelId);
    }



    @PostMapping("/upload/{idord}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("idord" )long hotelId) {
        return    serviceHotel.uploadFile(file,hotelId);
    }

    @GetMapping("/countratings/{id}")
    public Long Countratings(@PathVariable("id") Long hotelId)
    {
        return serviceRating.countratingsByhotel(hotelId);
    }
}
