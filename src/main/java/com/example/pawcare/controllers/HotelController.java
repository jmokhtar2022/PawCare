package com.example.pawcare.controllers;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Rating;
import com.example.pawcare.services.hotel.Ihotel;
import com.example.pawcare.services.hotel.ResponseMessage;
import com.example.pawcare.services.hotel.ServiceHotel;
import com.example.pawcare.services.rating.ServiceRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:4200")
public class HotelController {
    @Autowired
    Ihotel ihotel;

    @Autowired
    ServiceHotel serviceHotel;

    @Autowired
    ServiceRating serviceRating;

    @PostMapping("/addHotel")

    public Hotel addHotel(@RequestParam("hotel") String hotel,@RequestParam("image") MultipartFile image)
    {
        Hotel htl = new Gson().fromJson(hotel, Hotel.class);
        String imgName = StringUtils.cleanPath(image.getOriginalFilename());
        byte[] bytes = imgName.getBytes();
        int image2=bytes.toString().hashCode();
        String imageName = ""+ image2;
        String path="C:/xampp/htdocs/img";
        try {
            Files.copy(image.getInputStream(), Paths.get(path + File.separator + image2 + ".jpg"));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        htl.setData(imageName);

        return ihotel.addHotel(htl);
    }

    @PutMapping("/updateHotel/{id}")

    public Hotel updateHotel(@RequestBody Hotel hotel, @PathVariable("id") Long hotelid)
    {
        return ihotel.updateHotel(hotel,hotelid);
    }

    @GetMapping("/findHotel/{id}")
    public Hotel retrieveHotel(@PathVariable("id") Long hotelId)
    {
        //Long r=serviceRating.countratingsByhotel(hotelId);
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





    @GetMapping("/countratings/{id}")
    public Long Countratings(@PathVariable("id") Long hotelId)
    {
        return serviceRating.countratingsByhotel(hotelId);
    }
}
