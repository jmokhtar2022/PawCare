package com.example.pawcare.services.pet;

import com.example.pawcare.entities.Pet;
import com.example.pawcare.repositories.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import org.springframework.util.FileSystemUtils;

import java.util.List;

@Service
public class PetServicesImp implements IPetServices{
    @Autowired
    IPetRepository petRepository;

    @Override
    public Pet AddPet(Pet pet) throws IOException {
       String imagePet=pet.getPicture();
        String fileName = imagePet.substring(imagePet.lastIndexOf("\\") + 1);
        moveFile("C://Users//jmokh//OneDrive//Bureau//"+fileName,"E://Angular projects//Appointment//src//assets//images//"+pet.getName()+".jpg");
        pet.setPicture(pet.getName()+".jpg");
        return petRepository.save(pet);
    }

    @Override
    public Pet UpdatePet(Pet updatePet,Long idPet) throws IOException {
        Pet existingPet = petRepository.findById(idPet).orElse(null);

        if (existingPet!=null) {

            String fileName = updatePet.getPicture().substring(updatePet.getPicture().lastIndexOf("\\") + 1);
            File sourceFile = new File("E://Angular projects//Appointment//src//assets//images//"+existingPet.getName()+".jpg");
            sourceFile.delete();
            existingPet.setName(updatePet.getName());
            existingPet.setSpecie(updatePet.getSpecie());
            existingPet.setGender(updatePet.getGender());
            existingPet.setWeight(updatePet.getWeight());
            existingPet.setColor(updatePet.getColor());
            existingPet.setSituation(updatePet.getSituation());
            existingPet.setPicture(updatePet.getName()+".jpg");
            petRepository.save(existingPet);
            System.out.println(fileName);
            moveFile("C://Users//jmokh//OneDrive//Bureau//"+fileName,"E://Angular projects//Appointment//src//assets//images//"+updatePet.getName()+".jpg");
            System.out.println(existingPet.getName());
        } else {
            throw new RuntimeException("Pet not found with idPet: " + idPet);
        }

        return existingPet;
    }
    @Override
    public void DeletePet(Long idPet) {
        Pet existingPet = petRepository.findById(idPet).orElse(null);
        if (existingPet!=null) {
            File sourceFile = new File("E://Angular projects//Appointment//src//assets//images//"+existingPet.getName()+".jpg");
            sourceFile.delete();
            petRepository.deleteById(idPet);
        }

    }
    @Override
    public List<Pet> GetAllPets() {
        return petRepository.findAll();
    }
    @Override
    public Pet GetPetById(Long idPet) {
        return petRepository.findById(idPet).get();
    }
    public void moveFile(String sourcePath, String destinationPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("Source file does not exist.");
        }

        if (destinationFile.exists()) {
            throw new IllegalArgumentException("Destination file already exists.");
        }

        // Move the file using Spring's FileSystemUtils
        FileSystemUtils.copyRecursively(sourceFile, destinationFile);



    }



}
