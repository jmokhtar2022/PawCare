package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Hotel implements Serializable {

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "hotel")
    @JsonIgnore
    private List<Reservation> reservations;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @NotBlank
    @Size(max = 50)
    private String nomHotel;

    @NotBlank
    @Size(max = 200)
    private String address;

    private int numtel;

    @Column(name = "data")
    private String data;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "hotel")
    @JsonIgnore
    private List<Rating> ratings;


}
