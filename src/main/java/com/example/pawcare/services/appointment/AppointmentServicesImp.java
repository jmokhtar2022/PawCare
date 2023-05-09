package com.example.pawcare.services.appointment;

import com.example.pawcare.entities.*;
import com.example.pawcare.repositories.IAppointmentRepository;
import com.example.pawcare.repositories.IPetRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AppointmentServicesImp implements IAppointmentServices {

    @Autowired
    IAppointmentRepository appointmentRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPetRepository petRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Appointment AddAppointment(Appointment appointment) {
       // appointment.setUser(GetUserFromSession());
        appointment.setStatus(Status.Confirmed);
        if ((appointment.getReason()== Reason.Adoption) || (appointment.getReason()== Reason.Training) || (appointment.getReason()== Reason.Hotel_Reservation))
        {
            appointment.setDoctor(null);
        }
            appointmentRepository.save(appointment);
        return appointment;
    }

    @Override
    public Appointment UpdateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElse(null);
        if (existingAppointment != null) {
            existingAppointment.setPet(updatedAppointment.getPet());
          //  existingAppointment.setUser(GetUserFromSession());
            existingAppointment.setStartDate(updatedAppointment.getStartDate());
            existingAppointment.setEndDate(updatedAppointment.getEndDate());
            existingAppointment.setReason(updatedAppointment.getReason());
            existingAppointment.setLocation(updatedAppointment.getLocation());
            existingAppointment.setNotes(updatedAppointment.getNotes());
            existingAppointment.setPrix(updatedAppointment.getPrix());
            existingAppointment.setPet(updatedAppointment.getPet());

            if ((updatedAppointment.getReason()== Reason.Adoption) || (updatedAppointment.getReason()== Reason.Training) || (updatedAppointment.getReason()== Reason.Hotel_Reservation))
            {
                existingAppointment.setDoctor(null);
            }
            else
            {existingAppointment.setDoctor(updatedAppointment.getDoctor());}
            existingAppointment.setStatus(Status.Rescheduled);
            appointmentRepository.save(existingAppointment);
        } else {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
        return existingAppointment;
    }

    @Transactional
    public void DeleteAppointment(Long idAppointment) {
        Appointment appointment = appointmentRepository.findById(idAppointment).orElseThrow(() -> new NotFoundException("Appointment not found"));
        User user = appointment.getUser();
        User doctor = appointment.getDoctor();
        Pet pet = appointment.getPet();
        if (user != null) {
            user.getAppointments().remove(appointment);
            userRepository.save(user);
            appointment.setUser(null);
        }
        if (doctor != null) {
            doctor.getApt_doctors().remove(appointment);
            userRepository.save(doctor);
            appointment.setDoctor(null);
        }
        if (pet != null) {
            pet.getAppointments().remove(appointment);
            petRepository.save(pet);
            appointment.setPet(null);
        }
        appointmentRepository.delete(appointment);
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
        return appointmentRepository.findByPetNameLike("%" + name + "%");
    }

    public Integer GetAppointmentsNumber() {
        return appointmentRepository.findAll().size();
    }

    public long countRecordsAddedLast24Hours() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        return appointmentRepository.countByCreatedAtAfter(startTime);
    }

    @Override
    public List<Appointment> GetAppointmentsByClientId(Long id) {
        return appointmentRepository.GetAppointmentsByClientId(id);
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        List<Appointment> clientAppointments = appointmentRepository.getClientAppointmentsByUserId(userId);
        List<Appointment> doctorAppointments = appointmentRepository.getDoctorAppointmentsByUserId(userId);
        List<Appointment> combinedAppointments = new ArrayList<>();
        combinedAppointments.addAll(clientAppointments);
        combinedAppointments.addAll(doctorAppointments);
        return combinedAppointments;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }
    public User GetUserFromSession()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Optional<User> userOptional = userRepository.findByUsername(username);
                assert userOptional.orElse(null) != null;
                return userOptional.orElse(null);
            } else {
                String username = principal.toString();
                Optional<User> userOptional = userRepository.findByUsername(username);
                assert userOptional.orElse(null) != null;
                return userOptional.orElse(null);
            }
        }
        return null;
    }

    @Scheduled(cron = "30 56 15 * * * ")
    public void SendEmail()
    {

        List<Appointment> appointments=appointmentRepository.GetListAppointmentsTomail();

        for (Appointment appointment:appointments) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("j.mokhtarr@gmail.com");
            message.setTo(appointment.getUser().getEmail());
            message.setText("Dear "+appointment.getUser().getUsername()+",\n" +
                    "\n" +
                    "This is a friendly reminder for your upcoming appointment scheduled for "+appointment.getStartDate()+".\n" +
                    "\n" +
                    "Please make sure to arrive 10 minutes early to complete any necessary information about your Pet. If you are unable to attend, please reschedule your appointment before 24 hours.\n" +
                    "\n" +
                    "We look forward to seeing you soon.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Pawcare");
                    message.setSubject("Appointment Reminder:"+appointment.getStartDate());
                    mailSender.send(message);
                    log.info("Mail Send...");
        }


    }

    @Scheduled(cron = "* 55 23 25 * *")
    public void purgeAppointment()
    {
        List<Appointment> appointmentList=appointmentRepository.GetListAppointmentsToPurge();
        for (Appointment appointment:appointmentList ) {
            log.info(appointment.getLocation().toString());
            DeleteAppointment(appointment.getIdAppointment());
        }

    }
}
