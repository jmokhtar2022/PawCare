package com.example.pawcare.controllers;

import com.example.pawcare.entities.Pet;
import com.example.pawcare.services.pet.PetServicesImp;
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
    @PutMapping("/updatePet/{id}")
    public Pet ModifyPet(@RequestBody Pet pet,@PathVariable("id")Long idPet)
    {
        return petServicesImp.UpdatePet(pet,idPet);
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
    @GetMapping("/GetPet/{id}")
    public Pet RetrievePetById(@PathVariable("id") Long Idpet)
    {
        return petServicesImp.GetPetById(Idpet);
    }

}
