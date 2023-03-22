package com.example.pawcare.controllers;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.services.appointment.AppointmentServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "*")
public class AppointmentRestController {

    @Autowired
    AppointmentServicesImp appointmentServicesImp;

    @PostMapping("/addAppointment")
    public Appointment AppendAppointment(@RequestBody Appointment appointment)
    {
        return appointmentServicesImp.AddAppointment(appointment);
    }
    @PutMapping("/updateAppointment/{id}")
    public Appointment modifyAppointment(@PathVariable("id")Long idAppointment, @RequestBody Appointment appointment)
    {
        return appointmentServicesImp.UpdateAppointment(idAppointment,appointment);
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

    @GetMapping("/GetAppointment/{id}")
    public Appointment RetrieveAAppointmentByid(@PathVariable("id")Long idAppointment)
    {
        return appointmentServicesImp.GetAppointmentById(idAppointment);
    }
}
