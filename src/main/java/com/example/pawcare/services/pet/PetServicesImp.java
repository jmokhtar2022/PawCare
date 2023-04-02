package com.example.pawcare.services.pet;

import com.example.pawcare.entities.Pet;
import com.example.pawcare.repositories.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import org.springframework.util.FileSystemUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PetServicesImp implements IPetServices{
    @Autowired
    IPetRepository petRepository;

    @Override
    public Pet AddPet(Pet pet) throws IOException {
       String imagePet=pet.getPicture();
        String fileName = imagePet.substring(imagePet.lastIndexOf("\\") + 1);
        moveFile("C://Users//jmokh//OneDrive//Bureau//"+fileName,"E://Angular projects//PiAngular//src//assets//Petimages//"+pet.getName()+".jpg");
        pet.setPicture(pet.getName()+".jpg");
        return petRepository.save(pet);
    }

    @Override
    public Pet UpdatePet(Pet updatePet, Long idPet) throws IOException {
        Pet existingPet = petRepository.findById(idPet).orElse(null);
        String oldName = existingPet.getName();
        String newName = updatePet.getName();

        if (existingPet != null) {
            existingPet.setName(updatePet.getName());
            existingPet.setSpecie(updatePet.getSpecie());
            existingPet.setGender(updatePet.getGender());
            existingPet.setWeight(updatePet.getWeight());
            existingPet.setColor(updatePet.getColor());
            existingPet.setSituation(updatePet.getSituation());

            if (!oldName.equals(newName)) {
                String fileName = updatePet.getPicture().substring(updatePet.getPicture().lastIndexOf("\\") + 1);
                File sourceFile = new File("E://Angular projects//PiAngular//src//assets//Petimages//" + oldName + ".jpg");
                sourceFile.delete();
                moveFile("C://Users//jmokh//OneDrive//Bureau//" + fileName, "E://Angular projects//PiAngular//src//assets//Petimages//" + updatePet.getName() + ".jpg");
                existingPet.setPicture(updatePet.getName() + ".jpg");
            } else {
                existingPet.setPicture(updatePet.getPicture());
            }

            petRepository.save(existingPet);
        } else {
            throw new RuntimeException("Pet not found with idPet: " + idPet);
        }

        return existingPet;
    }

    @Override
    public void DeletePet(Long idPet) {
        Pet existingPet = petRepository.findById(idPet).orElse(null);
        if (existingPet!=null) {
            File sourceFile = new File("E://Angular projects//PiAngular//src//assets//Petimages//"+existingPet.getName()+".jpg");
            sourceFile.delete();
            petRepository.delete(existingPet);
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

    @Override
    public List<Pet> findPetByName(String name) {
        return petRepository.findByNameLike("%"+name+"%");
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
    public Integer GetpetsNumber()
    {
        return petRepository.findAll().size();
    }
    public long countRecordsAddedLast24Hours() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        return petRepository.countByCreatedAtAfter(startTime);
    }




}
