package com.example.pawcare.services.EmailAdoption;
import com.example.pawcare.entities.EmailDetails;

// Interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
}