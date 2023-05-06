package com.example.pawcare.services.appointment;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.entities.User;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentServices {

    Appointment AddAppointment (Appointment appointment);
    Appointment UpdateAppointment (Long idAppointment,Appointment appointment);
    void DeleteAppointment (Long IdAppointment);
    List<Appointment> GetAllAppointments();
    Appointment GetAppointmentById(Long IdAppointment);
    List<Appointment> FindAppointmentByPetName(String name);
    Integer GetAppointmentsNumber();
    long countRecordsAddedLast24Hours();
    List<Appointment>GetAppointmentsByClientId(Long id);
    List<Appointment> getAppointmentsByUserId(Long userId);
    String getCurrentUsername();
    User GetUserFromSession();




}
