package com.example.pawcare.services.accessory;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface AccessoryServices {

    public List<Accessory> retrieveAllAccessories();

    public Accessory addAccessory(Accessory accessory);

 //   public Accessory updateAccessory(Accessory accessory, Long idAccessory);

    public Accessory updateAccessory( Long idAccessory,Accessory accessory);
    public Accessory retrieveAccessoryById(Long idAccessory);

    public void deleteAccessory(Long idAccessory);

    public byte[] generatePdf(Accessory accessory) throws IOException ;

    }