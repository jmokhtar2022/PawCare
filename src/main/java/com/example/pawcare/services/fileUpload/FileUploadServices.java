package com.example.pawcare.services.fileUpload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServices {

    /*public String uploadfile1(MultipartFile file) throws IllegalStateException, IOException {
        String imageUrl = "http://localhost:8080/pawcareupload/" + file.getOriginalFilename();
        //
        file.transferTo(new File("C:\\pawcareupload\\" + file.getOriginalFilename()));
        return imageUrl;
    }*/

    public String uploadfile1(MultipartFile file) throws IllegalStateException, IOException {
        String filePath = "C:\\xampp\\htdocs\\img\\" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return "http://localhost:8080/img/" + file.getOriginalFilename();
    }




}
