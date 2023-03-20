package com.example.pawcare.services.appointment.pet;

import com.example.pawcare.entities.Pet;
import com.example.pawcare.repositories.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServicesImp implements IPetServices{
    @Autowired
    IPetRepository petRepository;

    @Override
    public Pet AddPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet UpdatePet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void DeletePet(Long idPet) {
        petRepository.deleteById(idPet);
    }

    @Override
    public List<Pet> GetAllPets() {
        return petRepository.findAll();
    }
}
