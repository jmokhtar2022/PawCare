package com.example.pawcare.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Hotel implements Serializable {

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "hotel")
    private List<Reservation> reservations;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long hotelId;
    private String nomHotel;
    private String address;
    private int numtel;

}
