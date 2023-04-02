package com.example.pawcare.controllers;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Reservation;
import com.example.pawcare.services.reservation.Ireservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    Ireservation ireservation;

    @PostMapping("/addreservation")
    public Reservation addReservation(@RequestBody Reservation reservation )
    {
        return ireservation.addreservation(reservation);
    }

    @PostMapping("/updatereservation")
    public Reservation updateReservation(@RequestBody Reservation reservation)
    {
        return ireservation.updatereservation(reservation);
    }

    @GetMapping("/findreservation/{id}")
    public Reservation retrieveReservation(@PathVariable("id") Long reservationId)
    {
        return ireservation.retrievereservation(reservationId);
    }

    @GetMapping("/all")
    public List<Reservation> findAllReservations()
    {
        return ireservation.RetriveALLreseervation();
    }

    @DeleteMapping("/deletereservation/{id}")
    public void deleteReservation(@PathVariable("id") Long reservationId)
    {
        ireservation.removereservation(reservationId);
    }
}
