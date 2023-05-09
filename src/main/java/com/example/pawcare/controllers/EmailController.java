package com.example.pawcare.controllers;
import com.example.pawcare.entities.EmailDetails;
import com.example.pawcare.services.EmailAdoption.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Annotation
@RestController
@CrossOrigin("*")
@RequestMapping("/email")
// Class
public class EmailController {
    @Autowired private EmailService emailService;

    // Sending a simple Email
 /*   @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendSimpleMail(details);
        return status;
    }
*/
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails details) {
        String result = emailService.sendSimpleMail(details);
        return ResponseEntity.ok(result);
    }



}

