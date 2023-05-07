package com.example.pawcare.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Reservation implements Serializable {

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private Pet pet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    @Temporal(TemporalType.DATE)
    private Date checkin;
    @Temporal(TemporalType.DATE)
    private Date checkout;
    private Status status;
    private String specialrequests;

}
