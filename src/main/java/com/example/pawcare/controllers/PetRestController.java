package com.example.pawcare.controllers;

import com.example.pawcare.entities.Pet;
import com.example.pawcare.services.pet.PetServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth/pet")
public class PetRestController {
    @Autowired
    PetServicesImp petServicesImp;

    @PostMapping("/addPet")
    public Pet AppendPet(@Valid @RequestBody Pet pet) throws IOException {
        return petServicesImp.AddPet(pet);
    }
    @PutMapping("/updatePet/{id}")
    public Pet ModifyPet(@Valid @RequestBody Pet pet,@PathVariable("id")Long idPet) throws IOException {
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

    @GetMapping("/GetPetByName/{name}")
        public List<Pet> GetPetByName(@PathVariable("name")String name)
    {
        return petServicesImp.findPetByName(name);
    }

    @GetMapping("/GetPetsNumber")
    public Integer FindPetsNumber()
    {
        return petServicesImp.GetpetsNumber();
    }

    @GetMapping("/GetPetsNumberAddedLast24")
    public Long GetPetsNumberAddedLast24()
    {
        return petServicesImp.countRecordsAddedLast24Hours();
    }
    @GetMapping("/GetPetsbyUserid/{id}")
    public List<Pet> GetPetsbyUserid(@PathVariable("id") Long idUser)
    {
        return petServicesImp.GetPetbyuser(idUser);
    }


}
