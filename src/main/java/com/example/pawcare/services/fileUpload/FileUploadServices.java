package com.example.pawcare.services.fileUpload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadServices {

    public String uploadfile1(MultipartFile file) throws IllegalStateException, IOException {
        String imageUrl = "http://localhost:8080/pawcareupload/" + file.getOriginalFilename();
        file.transferTo(new File("C:\\pawcareupload\\" + file.getOriginalFilename()));
        return imageUrl;
    }

}
