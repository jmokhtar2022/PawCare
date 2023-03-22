package com.example.pawcare.services.appointment;

import com.example.pawcare.entities.Appointment;

import java.util.List;

public interface IAppointmentServices {

    Appointment AddAppointment (Appointment appointment);
    Appointment UpdateAppointment (Long idAppointment,Appointment appointment);
    void DeleteAppointment (Long IdAppointment);
    List<Appointment> GetAllAppointments();
    Appointment GetAppointmentById(Long IdAppointment);


}
