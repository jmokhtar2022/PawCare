package com.example.pawcare.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Adoption {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdoption;
    private String description;
    private int nbDemande;
    private int nbLikes;
    @Temporal(TemporalType.DATE)
    private Date cDate;
    private String location;

    @OneToOne
    private Pet pet;




}
