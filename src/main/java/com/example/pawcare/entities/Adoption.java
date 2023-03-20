package com.example.pawcare.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Adoption implements Serializable {

    @OneToOne
    private Pet pet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdoption;
    private String description;
    private int nbDemande;
    private int nbLikes;
    @Temporal(TemporalType.DATE)
    private Date cDate;
    private String location;






}
