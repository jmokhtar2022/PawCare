package com.example.pawcare.services.mailapi;

import com.example.pawcare.entities.EmailDetails;

public interface IEmail {

    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
