package com.example.pawcare.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPet;
    private String name;
    private String specie;
    private String gender;
    private Float weight;
    private String color;
    private String picture;
    private Situation situation;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private Appointment appointment;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private Adoption adoption;

    @ManyToMany
    private List<Training> trainings;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private List<Reservation> reservations;
}
