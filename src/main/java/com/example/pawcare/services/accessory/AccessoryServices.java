package com.example.pawcare.services.accessory;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface AccessoryServices {

    public List<Accessory> retrieveAllAccessories();

    public Accessory addAccessory(Accessory accessory);

    public Accessory updateAccessory( Long idAccessory,Accessory accessory);
    public Accessory retrieveAccessoryById(Long idAccessory);

    public void deleteAccessory(Long idAccessory);

    public byte[] generatePdf(Accessory accessory) throws IOException ;
    public Page<Accessory> findAll(int page, int size, String sortField, String sortDir) ;
    public List<Accessory> searchAccessories(String name, Float price) ;

    public String ConvertAccessoriesToCsv(List<Accessory> accessories) throws IOException ;
    public void ExportAccessoriesToCsv(HttpServletResponse servletResponse) throws IOException ;


    }