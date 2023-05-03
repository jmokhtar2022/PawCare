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


import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
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
        return ireservation.addreservation(reservation);
    }

    @PostMapping("/updatereservation")
    public Reservation updateReservation(@RequestBody Reservation reservation)
    {
        return ireservation.updatereservation(reservation);
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
            MatrixToImageWriter.writeToStream(bitMatrix,"PNG",outputStream);
        }catch (WriterException | IOException e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    /*public static void GenerateImageQRCode(String txt, int width, int height, String path){
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        try {
            BitMatrix bitMatrix=qrCodeWriter.encode(txt,BarcodeFormat.QR_CODE,width,height);
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG", FileSystems.getDefault().getPath(path));
        }catch (WriterException | IOException e){
            e.printStackTrace();
        }
    }*/

    private final String imagepath= "./src/main/resources/qr";
    @GetMapping("/findreservationby/{id}")
    public Reservation retrieveReservationqr(@PathVariable("id") Long reservationId)
    {
        GenerateByteQRCode(reservationId.toString(),250,250,imagepath);
        return ireservation.retrievereservation(reservationId);
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

