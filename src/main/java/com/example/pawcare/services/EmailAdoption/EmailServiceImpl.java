package com.example.pawcare.services.EmailAdoption;
import com.example.pawcare.entities.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailServiceImpl implements EmailService {
    @Autowired private JavaMailSender javaMailSender;

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
            return "Error while Sending Mail";
        }
    }


}
