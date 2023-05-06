package com.example.pawcare.services.pet;

import com.example.pawcare.entities.Pet;
import java.io.IOException;
import java.util.List;

public interface IPetServices  {
    Pet AddPet(Pet pet) throws IOException;
    Pet UpdatePet(Pet pet,Long idPet) throws IOException;
    void DeletePet(Long idPet);
    List<Pet> GetAllPets();
    Pet GetPetById(Long idPet);
    List<Pet> findPetByName(String name);
    Integer GetpetsNumber();
    long countRecordsAddedLast24Hours();
//    List<Pet>GetPetbyuser(Long idUser);





}
