package com.example.pawcare.controllers;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.services.appointment.AppointmentServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth/appointment")
public class AppointmentRestController {

    @Autowired
    AppointmentServicesImp appointmentServicesImp;

    @PreAuthorize("hasAnyRole('ROLE_VETERINARY','ROLE_ADMIN','ROLE_CLIENT')")
    @PostMapping("/addAppointment")
    public Appointment AppendAppointment(@Valid @RequestBody Appointment appointment)
    {
        return appointmentServicesImp.AddAppointment(appointment);
    }
    @PutMapping("/updateAppointment/{id}")
    public Appointment modifyAppointment(@PathVariable("id")Long idAppointment,@Valid @RequestBody Appointment appointment)
    {
        return appointmentServicesImp.UpdateAppointment(idAppointment,appointment);
    }
    @DeleteMapping("/deleteAppointment/{id}")
    public void RemoveAppointment(@PathVariable("id")Long idAppointment)
    {
        appointmentServicesImp.DeleteAppointment(idAppointment);
    }
    @PreAuthorize("hasAnyRole('ROLE_VETERINARY','ROLE_ADMIN','ROLE_CLIENT')")

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
    @GetMapping("/GetAppointmentByPet/{name}")
    public List<Appointment> RetrieveAppointmentByPet(@PathVariable("name")String name)
    {
        return appointmentServicesImp.FindAppointmentByPetName(name);
    }
    @GetMapping("/GetAppointmentsNumber")
    public Integer FindAppointmentsNumber()
    {
        return appointmentServicesImp.GetAppointmentsNumber();
    }

    @GetMapping("/GetPetsNumberAddedLast24")
    public Long GetPetsNumberAddedLast24()
    {
        return appointmentServicesImp.countRecordsAddedLast24Hours();
    }

    @GetMapping("/GetAppointmentsByClientId/{id}")
    public List<Appointment> GetAppointmentsByClientId(@PathVariable("id")Long id)
    {
        return appointmentServicesImp.GetAppointmentsByClientId(id);
    }

    @GetMapping("/getAppointmentsByUserId/{id}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable("id")Long id)
    {
        return appointmentServicesImp.getAppointmentsByUserId(id);
    }

    @GetMapping("/getCurrentUsername")
    public String getCurrentUsername()
    {
        return appointmentServicesImp.getCurrentUsername();
    }


}
