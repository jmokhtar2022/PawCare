package com.example.pawcare.services.pet;

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
    public Pet UpdatePet(Pet updatePet,Long idPet) {

        Pet existingPet = petRepository.findById(idPet).orElse(null);
        if (existingPet!=null) {
            existingPet.setName(updatePet.getName());
            existingPet.setSpecie(updatePet.getSpecie());
            existingPet.setGender(updatePet.getGender());
            existingPet.setWeight(updatePet.getWeight());
            existingPet.setColor(updatePet.getColor());
            existingPet.setPicture(updatePet.getPicture());
            existingPet.setSituation(updatePet.getSituation());
            petRepository.save(existingPet);
        } else {
            throw new RuntimeException("Pet not found with idPet: " + idPet);
        }

        return existingPet;
    }

    @Override
    public void DeletePet(Long idPet) {
        petRepository.deleteById(idPet);
    }

    @Override
    public List<Pet> GetAllPets() {
        return petRepository.findAll();
    }
    @Override
    public Pet GetPetById(Long idPet) {
        return petRepository.findById(idPet).get();
    }
}
