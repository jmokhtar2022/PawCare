package com.example.pawcare.controllers;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.services.abonnement.AppointmentServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentRestController {

    @Autowired
    AppointmentServicesImp appointmentServicesImp;

    @PostMapping("/addAppointment")
    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('admin')")
    public Appointment AppendAppointment(@RequestBody Appointment appointment)
    {
        return appointmentServicesImp.AddAppointment(appointment);
    }
    @PutMapping("/updateAppointment")
    @PreAuthorize("hasRole('admin')")
    public Appointment modifyAppointment(@RequestBody Appointment appointment)
    {
        return appointmentServicesImp.UpdateAppointment(appointment);
    }
    @DeleteMapping("/deleteAppointment/{id}")
    public void RemoveAppointment(@PathVariable("id")Long idAppointment)
    {
        appointmentServicesImp.DeleteAppointment(idAppointment);
    }
    @GetMapping("/all")
    public List<Appointment>RetrieveAllAppointments()
    {
        return appointmentServicesImp.GetAllAppointments();
    }
}
