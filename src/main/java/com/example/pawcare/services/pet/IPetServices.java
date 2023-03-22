package com.example.pawcare.services.pet;


import com.example.pawcare.entities.Pet;

import java.util.List;

public interface IPetServices  {
    Pet AddPet(Pet pet);
    Pet UpdatePet(Pet pet,Long idPet);
    void DeletePet(Long idPet);
    List<Pet> GetAllPets();
    Pet GetPetById(Long idPet);


}
