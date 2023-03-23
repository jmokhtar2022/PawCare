package com.example.pawcare.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private String title;
    private String description;
    private int nbDemande;
    private int nbLikes;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime cDate;
    private String location;
    @Lob
    private byte[] file;






}
