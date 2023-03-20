package com.example.pawcare.services.appointment.pet;


import com.example.pawcare.entities.Pet;

import java.util.List;

public interface IPetServices  {
    Pet AddPet(Pet pet);
    Pet UpdatePet(Pet pet);
    void DeletePet(Long idPet);
    List<Pet> GetAllPets();


}
