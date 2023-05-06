package com.example.pawcare.controllers;

import com.example.pawcare.services.GeneralLog.GeneralLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth/generalLogs")
public class GeneralLogRestController {

    private final GeneralLogService generalLogService;

    public GeneralLogRestController(GeneralLogService generalLogService) {
        this.generalLogService = generalLogService;
    }

    @PostMapping("/download")
    public ResponseEntity<String> getAllGeneralLogsCsv() throws IOException {
        File file = new File("C://Users//jmokh//OneDrive//Bureau//logs//logs.csv");
        generalLogService.saveAllGeneralLogsToCsv(String.valueOf(file));
        return ResponseEntity.ok("General logs saved to CSV file: " + file);
    }
}

