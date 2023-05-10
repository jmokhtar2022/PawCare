package com.example.pawcare.services.accessory;

import com.example.pawcare.entities.Accessory;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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