package com.example.pawcare.services.appointment;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.entities.Status;
import com.example.pawcare.repositories.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
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
    public Appointment UpdateAppointment(Long id, Appointment updatedAppointment) {

        Appointment existingAppointment = appointmentRepository.findById(id).orElse(null);
        if (existingAppointment != null) {
            existingAppointment.setPet(updatedAppointment.getPet());
            existingAppointment.setUser(updatedAppointment.getUser());
            existingAppointment.setStartDate(updatedAppointment.getStartDate());
            existingAppointment.setEndDate(updatedAppointment.getEndDate());
            existingAppointment.setReason(updatedAppointment.getReason());
            existingAppointment.setLocation(updatedAppointment.getLocation());
            existingAppointment.setNotes(updatedAppointment.getNotes());
            existingAppointment.setPrix(updatedAppointment.getPrix());
            existingAppointment.setPet(updatedAppointment.getPet());
            LocalDateTime startDate = existingAppointment.getStartDate();
            LocalDateTime endDate = existingAppointment.getEndDate();
            if (startDate.isBefore(endDate)) {
                List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(startDate, endDate);
                conflictingAppointments.remove(existingAppointment);
                if (conflictingAppointments.isEmpty()) {
                    existingAppointment.setStatus(Status.Rescheduled);
                    appointmentRepository.save(existingAppointment);
                } else {
                    throw new RuntimeException("Appointment conflicts with existing appointments");
                }
            } else {
                throw new RuntimeException("Start date must be before end date");
            }
        } else {
            throw new RuntimeException("Appointment not found with id: " + id);
        }

        return existingAppointment;
    }
    @Override
    public void DeleteAppointment(Long idAppointment) {
        Appointment existingAppointment = appointmentRepository.findById(idAppointment).get();
        appointmentRepository.delete(existingAppointment);
    }
    @Override
    public List<Appointment> GetAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment GetAppointmentById(Long IdAppointment) {
        return appointmentRepository.findById(IdAppointment).get();
    }
    @Override
    public List<Appointment> FindAppointmentByPetName(String name) {
        return appointmentRepository.findByPetNameLike("%"+name+"%");
    }

    public Integer GetAppointmentsNumber()
    {
        return appointmentRepository.findAll().size();
    }

    public long countRecordsAddedLast24Hours() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        return appointmentRepository.countByCreatedAtAfter(startTime);
    }

}
