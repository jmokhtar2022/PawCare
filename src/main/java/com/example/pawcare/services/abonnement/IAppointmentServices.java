package com.example.pawcare.services.abonnement;

import com.example.pawcare.entities.Appointment;

import java.util.List;

public interface IAppointmentServices {

    Appointment AddAppointment (Appointment appointment);
    Appointment UpdateAppointment (Appointment appointment);
    void DeleteAppointment (Long IdAppointment);
    List<Appointment> GetAllAppointments();

}
