package com.example.pawcare.services.reservation;

import com.example.pawcare.entities.Hotel;
import com.example.pawcare.entities.Reservation;
import com.example.pawcare.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceReservation implements Ireservation{
    @Autowired
    ReservationRepo reservationRepo;

    @Override
    public List<Reservation> RetriveALLreseervation() {

        return reservationRepo.findAll();
    }

    @Override
    public Reservation addreservation(Reservation reservation) {

        return reservationRepo.save(reservation);
    }

    @Override
    public Reservation updatereservation(Reservation reservation,Long resid) {
        Reservation xres = reservationRepo.findById(resid).get();
        xres.setCheckin(reservation.getCheckin());
        xres.setCheckout(reservation.getCheckout());
        xres.setStatus(reservation.getStatus());
        xres.setSpecialrequests(reservation.getSpecialrequests());

        return reservationRepo.save(reservation);
    }

    @Override
    public Reservation retrievereservation(Long idReservation) {

        return reservationRepo.findById(idReservation).get();
    }

    @Override
    public void removereservation(Long idReservation) {
        reservationRepo.deleteById(idReservation);

    }
}
