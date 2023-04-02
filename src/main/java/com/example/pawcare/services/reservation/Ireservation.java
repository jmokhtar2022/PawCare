package com.example.pawcare.services.reservation;


import com.example.pawcare.entities.Reservation;

import java.util.List;

public interface Ireservation {
    List<Reservation> RetriveALLreseervation();

    Reservation addreservation(Reservation reservation);

    Reservation updatereservation(Reservation reservation);

    Reservation retrievereservation(Long idReservation);

    void removereservation(Long idReservation);
}
