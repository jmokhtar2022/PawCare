package com.example.pawcare.services.hotel;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ServiceHotel implements Ihotel {

    @Autowired
    HotelRepository hotelRepository;

    Hotel hotel;

    @Override
    public List<Hotel> RetriveALLHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel addHotel(Hotel hotel) {

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel,Long hotelid)
    {
        Hotel xhotel= hotelRepository.findById(hotelid).get();
        xhotel.setNomHotel(hotel.getNomHotel());
        xhotel.setNumtel(hotel.getNumtel());
        xhotel.setAddress(hotel.getAddress());

        return hotelRepository.save(xhotel);
    }

    @Override
    public Hotel retrieveHotel(Long hotelId) {
        return hotelRepository.findById(hotelId).get();
    }

    @Override
    public void removeHotel(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }


   /* public ResponseEntity<ResponseMessage> uploadFile(MultipartFile file, long hotelId)
    {

        hotel=hotelRepository.findById(hotelId).get();
        String message = "";
        try {
            hotel.setData(file.getBytes());
            hotelRepository.save(hotel);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "! Erreur :"+ e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }*/



}
