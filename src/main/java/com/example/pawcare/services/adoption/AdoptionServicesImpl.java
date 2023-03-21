package com.example.pawcare.services.adoption;

import com.example.pawcare.entities.Adoption;
import com.example.pawcare.repositories.IAdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdoptionServicesImpl implements IAdoptionServices {

    @Autowired
    IAdoptionRepository adoptionRepository;
    private AdoptionServicesImpl(){}

    @Override
    public Adoption AddAdoption(Adoption adoption) {return adoptionRepository.save(adoption);}

    @Override
    public List<Adoption> ListAdoption()
    {return adoptionRepository.findAll();}

    public Adoption UpdateAdoption(Adoption adoption)
    {return adoptionRepository.save(adoption);}

    public Adoption RetrieveAdoptionById(long idAdoption)
    {return adoptionRepository.findById(idAdoption).get();}

    public Adoption DeleteAdoption(long idAdoption) {adoptionRepository.deleteById(idAdoption); return null;}
}
