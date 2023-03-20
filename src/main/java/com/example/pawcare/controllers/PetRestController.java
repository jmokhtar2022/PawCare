package com.example.pawcare.controllers;

import com.example.pawcare.entities.Pet;
import com.example.pawcare.services.appointment.pet.PetServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetRestController {
    @Autowired
    PetServicesImp petServicesImp;

    @PostMapping("/addPet")
    public Pet AppendPet(@RequestBody Pet pet)
    {
        return petServicesImp.AddPet(pet);
    }
    @PutMapping("/updatePet")
    public Pet ModifyPet(@RequestBody Pet pet)
    {
        return petServicesImp.UpdatePet(pet);
    }
    @DeleteMapping("/deletePet/{id}")
    public void RemovePet(@PathVariable("id") Long idPet)
    {
        petServicesImp.DeletePet(idPet);
    }
    @GetMapping("/GetAll")
    public List<Pet> RetrieveALLPets()
    {
        return petServicesImp.GetAllPets();
    }
}