package com.example.pawcare.services.adoption;

import com.example.pawcare.entities.Adoption;
import com.example.pawcare.entities.Training;
import com.example.pawcare.repositories.IAdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionServicesImpl implements IAdoptionServices {

    @Autowired
    IAdoptionRepository adoptionRepository;
    private AdoptionServicesImpl(){}

    @Override
    public Adoption AddAdoption(Adoption adoption) {

        return adoptionRepository.save(adoption);}

    @Override
    public List<Adoption> ListAdoption()
    {return adoptionRepository.findAll();}

    public Adoption UpdateAdoption(Adoption adoption)
    {return adoptionRepository.save(adoption);}

    public Adoption RetrieveAdoptionById(long idAdoption)
    {return adoptionRepository.findById(idAdoption).get();}

    public Adoption DeleteAdoption(long idAdoption) {adoptionRepository.deleteById(idAdoption); return null;}


    public ResponseEntity<Integer> like(Long idAdoption) {
        Optional<Adoption> optionalAdoption = adoptionRepository.findById(idAdoption);
        if (optionalAdoption.isPresent()) {
            Adoption adoption = optionalAdoption.get();
            int like = adoption.getNbLikes();
            adoption.setNbLikes(like + 1);
            adoptionRepository.save(adoption);
            return ResponseEntity.ok().body(adoption.getNbLikes());
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
