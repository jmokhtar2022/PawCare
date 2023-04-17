package com.example.pawcare.controllers;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.services.accessory.AccessoryServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import java.util.List;
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/accessory")
public class AccessoryController {
    @Autowired
    ServletContext context;

    @Autowired
    AccessoryServices accessoryServices;

    @GetMapping("/listaccessories")
    public List<Accessory> listaccessories() {
        return accessoryServices.retrieveAllAccessories();
    }

    @PostMapping("/addAccessory")
    public Accessory addAccessory(@RequestBody Accessory accessory) {
        return accessoryServices.addAccessory(accessory);

    }

    @PutMapping(value = "/updateAccessory/{idAccessory}")

    public Accessory updateAccessory(@PathVariable Long idAccessory, @RequestBody Accessory accessory) {
        return accessoryServices.updateAccessory( idAccessory,accessory);

    }
    @DeleteMapping("/deleteAccessory/{idAccessory}")

    public void deleteAccessory(@PathVariable("idAccessory") Long idAccessory) {
        accessoryServices.deleteAccessory(idAccessory);
    }

    @GetMapping("/getAccessoryById/{idAccessory}")
    public Accessory GetAccessoryById(@PathVariable("idAccessory") Long idAccessory) {
        return accessoryServices.retrieveAccessoryById(idAccessory);
    }


}


