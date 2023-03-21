package com.example.pawcare.services.adoption;

import com.example.pawcare.entities.Adoption;

import java.util.List;

public interface IAdoptionServices {

    public Adoption AddAdoption(Adoption adoption);
    List<Adoption> ListAdoption ();
    public Adoption UpdateAdoption(Adoption adoption);
    public Adoption RetrieveAdoptionById(long idAdoption);
    public Adoption DeleteAdoption(long idAdoption);
}
