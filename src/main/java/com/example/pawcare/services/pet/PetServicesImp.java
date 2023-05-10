package com.example.pawcare.services.pet;

import com.example.pawcare.entities.Appointment;
import com.example.pawcare.entities.Pet;
import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.IAppointmentRepository;
import com.example.pawcare.repositories.IPetRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PetServicesImp implements IPetServices{
    @Autowired
    IPetRepository petRepository;
    @Autowired
    IAppointmentRepository appointmentRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Pet AddPet(Pet pet) throws IOException {
       String imagePet=pet.getPicture();
        String fileName = imagePet.substring(imagePet.lastIndexOf("\\") + 1);
        moveFile("C://Users//jmokh//OneDrive//Bureau//"+fileName,"D://User-apt//PawCareAngular//src//assets//Petimages//"+pet.getName()+".jpg");
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
                File sourceFile = new File("D://User-apt//PawCareAngular//src//assets//Petimages//" + oldName + ".jpg");
                sourceFile.delete();
                moveFile("C://Users//jmokh//OneDrive//Bureau//" + fileName, "D://User-apt//PawCareAngular//src//assets//Petimages//" + updatePet.getName() + ".jpg");
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

    @Transactional
    @Override
    public void DeletePet(Long idPet) {
        Pet existingPet = petRepository.findById(idPet).orElse(null);
        if (existingPet != null) {
            Set<Appointment> appointments = existingPet.getAppointments();
            if (appointments != null) {
                Set<Appointment> appointmentsToDelete = new HashSet<>(appointments);
                for (Appointment appointment : appointmentsToDelete) {
                    User user = appointment.getUser();
                    if (user != null) {
                        user.getAppointments().remove(appointment);
                        userRepository.save(user);
                    }
                    existingPet.getAppointments().remove(appointment);
                    appointment.setPet(null);
                    appointment.setUser(null);
                    appointment.setDoctor(null);
                    appointmentRepository.delete(appointment);
                }
            }
            File sourceFile = new File("D://User-apt//PawCareAngular//src//assets//Petimages//" + existingPet.getName() + ".jpg");
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

    public List<Pet>GetPetbyuser(Long idUser){
        return petRepository.GetPetbyuser(idUser);
    }





}
