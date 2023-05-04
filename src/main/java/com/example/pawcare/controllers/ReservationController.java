package com.example.pawcare.controllers;

import com.example.pawcare.entities.Hotel;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    Ireservation ireservation;

    @PostMapping("/addreservation")
    public Reservation addReservation(@RequestBody Reservation reservation )
    {
        String path="C:\\Users\\Firas\\Desktop\\qr\\";

        String qrname= "reservation"+reservation.getPet()+reservation.getStatus();

        reservation.setQRCode(qrname);

        try {
            generateQRCodeImage(reservation.getStatus().toString(), 250, 250, path+qrname +".jpg");
        }
        catch (WriterException | IOException e) {
            e.printStackTrace();
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




   /* @GetMapping("/qrcode/{id}")
    public Response getReservationQRCode(@PathVariable("id")Long reservationId){
        Reservation reservation=ireservation.retrievereservation(reservationId);
        try {

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(reservation.getIdReservation().toString(), BarcodeFormat.QR_CODE, 200, 200, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Convert image to base64-encoded string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageData = baos.toByteArray();
            String imageBase64 = Base64.getEncoder().encodeToString(imageData);

            // Return JSON response with QR code image
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("reservationCode", reservation.getIdReservation().toString());
            responseMap.put("qrCode", imageBase64);
            return Response.ok(responseMap).build();
        }catch (WriterException | IOException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        }*/
    }

