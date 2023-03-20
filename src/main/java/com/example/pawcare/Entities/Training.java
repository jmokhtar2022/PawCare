package com.example.pawcare.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Training implements Serializable {

    @ManyToMany(mappedBy = "trainings")
    private List<Pet> pets;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTraining;
    private long duration;
    private float price;
    private int nbrplaces;
    private Type type;
    @Temporal(TemporalType.DATE)
    private Date cDate;





}

