package com.example.pawcare.services.appointment;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.entities.Status;
import com.example.pawcare.repositories.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServicesImp implements IAppointmentServices{

    @Autowired
    IAppointmentRepository appointmentRepository;
    @Override
    public Appointment AddAppointment(Appointment appointment) {
        LocalDateTime startDate=appointment.getStartDate();
        LocalDateTime endDate=appointment.getEndDate();
        if (startDate.isBefore(endDate))
        {
        List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(startDate, endDate);
        if (conflictingAppointments.isEmpty()) {
                 appointment.setStatus(Status.Confirmed);
                appointmentRepository.save(appointment);
            }}
        return appointment;
    }
    @Override
    public Appointment UpdateAppointment(Appointment appointment) {
        LocalDateTime startDate=appointment.getStartDate();
        LocalDateTime endDate=appointment.getEndDate();
        if (startDate.isBefore(endDate))
        {
            List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(startDate, endDate);
            if (conflictingAppointments.isEmpty()) {
                appointment.setStatus(Status.Rescheduled);
                appointmentRepository.save(appointment);
            }}
        return appointment;
    }
    @Override
    public void DeleteAppointment(Long idAppointment) {
        appointmentRepository.deleteById(idAppointment);
    }
    @Override
    public List<Appointment> GetAllAppointments() {
        return appointmentRepository.findAll();
    }
}
