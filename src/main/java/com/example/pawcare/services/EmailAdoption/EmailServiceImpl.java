package com.example.pawcare.services.EmailAdoption;
import com.example.pawcare.entities.EmailDetails;
import com.example.pawcare.entities.Training;
import com.example.pawcare.repositories.ITrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j

public class EmailServiceImpl implements EmailService {
    @Autowired private JavaMailSender javaMailSender;
    @Autowired
    ITrainingRepository trainingRepository;

    // Method 1
    // To send a simple email
    public String sendSimpleMail(EmailDetails details)
    {// Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            // Setting up necessary details
            mailMessage.setFrom(details.getSenderEmail());
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            return e.getMessage();
        }

    }

    @Scheduled(cron = "0 0 9 * * *") // runs every day at 9am
    public void sendReport() {
        List<Training> reportedTrainings = trainingRepository.findReportedTrainings();
        if (reportedTrainings.isEmpty()) {
            return; // don't send an email if there are no reported trainings
        }
        String emailSubject = "Reported Training Sessions";
        String emailBody = "The following training sessions have been reported:\n";
        for (Training training : reportedTrainings) {
            emailBody += "Training #" + training.getIdTraining() + " - " + training.getTitle() + "\n";
        }
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("rania.rhaiem99@gmail.com"); // replace with your email address
        email.setSubject(emailSubject);
        email.setText(emailBody);
        javaMailSender.send(email);
    }
  //  @Scheduled(cron = "*/1 * * * * *")
   /* public void reportTrainingSessions() {
        List<Training> reportedTrainings = trainingRepository.findReportedTrainings();
        if (!reportedTrainings.isEmpty()) {
            log.info("Reported Training Sessions:");
            for (Training training : reportedTrainings) {
                log.info(training.getTitle() + " - " + training.getDescription());
            }
        }
    }*/

}



