package com.example.pawcare.controllers;


import com.example.pawcare.entities.Adoption;
import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.repositories.IAdoptionRepository;
import com.example.pawcare.repositories.ICommentAdoptionRepository;
import com.example.pawcare.services.adoption.IAdoptionServices;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/adoption")
public class AdoptionController {
    @Autowired
    IAdoptionServices adoptionServices;
    @Autowired
    IAdoptionRepository adoptionRepository;



    @PostMapping( "/adoption")
    public Adoption AddAdoption(@ModelAttribute Adoption adoption, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        LocalDate  currentDate = LocalDate.now();
        adoption.setCDate(currentDate);
        if (!imageFile.isEmpty()) {
            // Generate a unique filename for the uploaded file
            String fileName =imageFile.getOriginalFilename();
            // Store the file on the file system
            Path filePath = Paths.get("C:/Users/Rania/Downloads/Pi/img", fileName);
            //Files.write(filePath, imageFile.getBytes());
            // Set the file path as the value of the picture field
            adoption.setPicture(filePath.toString());
        }

        return adoptionServices.AddAdoption(adoption);

    }


    @GetMapping("/adoption")
    public List<Adoption> ListAdoption()
    {return adoptionServices.ListAdoption();}

    @GetMapping("/adoption/{id}")
    public Adoption RetrieveAdoptionById (@PathVariable("id") long idAdoption)
    {return adoptionServices.RetrieveAdoptionById(idAdoption);}

    @DeleteMapping("/adoption/{id}")
    public void DeleteAdoption (@PathVariable("id") long idAdoption)
    {adoptionServices.DeleteAdoption(idAdoption);}


    @PutMapping("/adoption/{id}")
    public Adoption updateAdoption(@PathVariable(value = "id") Long id,
                                   @ModelAttribute Adoption adoption,
                                   @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        Adoption existingAdoption = adoptionServices.RetrieveAdoptionById(id);
        if (existingAdoption == null) {
            throw new OpenApiResourceNotFoundException("Adoption not found with id: " + id);
        }
        LocalDate  currentDate = LocalDate.now();
        adoption.setCDate(currentDate);
        if (imageFile != null && !imageFile.isEmpty()) {
            // Generate a unique filename for the uploaded file
            String fileName = imageFile.getOriginalFilename();
            // Store the file on the file system
            Path filePath = Paths.get("C:/Users/Rania/Downloads/Pi/img", fileName);
            Files.write(filePath, imageFile.getBytes());
            // Set the file path as the value of the picture field
            adoption.setPicture(filePath.toString());
        } else {
            // If no image file is provided, use the existing picture field value
            adoption.setPicture(existingAdoption.getPicture());
        }
        adoption.setIdAdoption(id);
        return adoptionServices.UpdateAdoption(adoption);

    }



    @GetMapping("/adoption/{id}/picture")
    public ResponseEntity<byte[]> getAdoptionPicture(@PathVariable Long id) throws IOException {
        // Load the adoption object from the database
        Adoption adoption = adoptionServices.RetrieveAdoptionById(id);
        if (adoption == null) {
            return ResponseEntity.notFound().build();
        }

        // Load the image file from disk
        Path imagePath = Paths.get(adoption.getPicture());
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

        // Set the content type of the response to the MIME type of the image file
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Create the response entity with the image bytes and content type
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imageBytes);
    }





}





