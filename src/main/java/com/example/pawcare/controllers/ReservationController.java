package com.example.pawcare.controllers;

//import com.example.pawcare.entities.EmailDetails;
import com.example.pawcare.entities.Hotel;
//import com.example.pawcare.services.mailapi.EmailService;
//import com.example.pawcare.services.mailapi.IEmail;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.example.pawcare.entities.Reservation;
import com.example.pawcare.services.reservation.Ireservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

import java.security.SecureRandom;
import java.util.Random;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    Ireservation ireservation;


    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    @PostMapping("/addreservation/{email}")
    public Reservation addReservation(@RequestBody Reservation reservation ,@PathVariable("email") String email)
    {
        //String path="C:\\Users\\Firas\\Desktop\\qr\\";
        String path="C:\\xampp\\htdocs\\qr\\";
        String qrname=RandomStringGenerator.generate();
        //String qrname= "reservation"+reservation.getStatus();
        reservation.setQRCode(qrname);
        try {
            generateQRCodeImage(reservation.getStatus().toString(), 250, 250, path+qrname +".jpg");
        }
        catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        //trying mail methode here
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText("Hey! \n\n Your Reservation is sent successfully you can check the status of the reservation by scanning the qr code in the attechement \n\n Thanks");
            mimeMessageHelper.setSubject("Reservation sent successfully");

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(path+qrname +".jpg"));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);

        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred

        }


        return ireservation.addreservation(reservation);

    }

    @PutMapping("/updatereservation/{id}")
    public Reservation updateReservation(@RequestBody Reservation reservation,@PathVariable("id") Long id)
    {

        return ireservation.updatereservation(reservation,id);
    }

    @GetMapping("/findreservation/{id}")
    public Reservation retrieveReservation(@PathVariable("id") Long reservationId)
    {

        return ireservation.retrievereservation(reservationId);
    }

    @GetMapping("/all")
    public List<Reservation> findAllReservations()
    {
        return ireservation.RetriveALLreseervation();
    }

    @DeleteMapping("/deletereservation/{id}")
    public void deleteReservation(@PathVariable("id") Long reservationId)
    {
        ireservation.removereservation(reservationId);
    }
    public static byte[]GenerateByteQRCode(String txt, int width, int height, String path){
        ByteArrayOutputStream outputStream=null;
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        try {
            BitMatrix bitMatrix=qrCodeWriter.encode(txt, BarcodeFormat.QR_CODE,width,height);
            outputStream=new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"jpg",outputStream);
        }catch (WriterException | IOException e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }



    private final String imagepath= "./src/main/resources/qr";
    @GetMapping("/findreservationby/{id}")
    public Reservation retrieveReservationqr(@PathVariable("id") Long reservationId)
    {
        GenerateByteQRCode(reservationId.toString(),250,250,imagepath);
        return ireservation.retrievereservation(reservationId);
    }

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "jpg", path);

    }

    public static class RandomStringGenerator {
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static final int LENGTH = 10;

        public static String generate() {
            Random random = new SecureRandom();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < LENGTH; i++) {
                int index = random.nextInt(CHARACTERS.length());
                char randomChar = CHARACTERS.charAt(index);
                sb.append(randomChar);
            }
            return sb.toString();
        }
    }
   /* @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendSimpleMail(details);

        return status;
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details)
    {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }*/
    }

