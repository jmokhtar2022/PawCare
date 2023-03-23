package com.example.pawcare.controllers;


import com.example.pawcare.entities.Adoption;
import com.example.pawcare.services.adoption.IAdoptionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/adoption")
public class AdoptionController {
    @Autowired
    IAdoptionServices adoptionServices;

    @PostMapping("/adoption")
    public Adoption AddAdoption(@RequestBody Adoption adoption)
    {
        LocalDateTime currentDate = LocalDateTime.now();
        adoption.setCDate(currentDate);
        return adoptionServices.AddAdoption(adoption);}

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
    public Adoption UpdateAdoption (@PathVariable("id") long id,@RequestBody Adoption adoption)
    {   LocalDateTime currentDate = LocalDateTime.now();
        Adoption a=adoptionServices.RetrieveAdoptionById(id);
        a.setDescription(adoption.getDescription());
        a.setLocation(adoption.getLocation());
        a.setTitle(adoption.getTitle());
        adoption.setCDate(currentDate);
        return adoptionServices.UpdateAdoption(adoption);}
}
