package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Hotel hotel;

    @ManyToOne
    @JsonIgnore
    private Pet pet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
   // @Temporal(TemporalType.DATE)
   @JsonFormat(pattern = "yyyy-MM-dd")
   @Column(columnDefinition="DATETIME")
    private Date checkin;
    //@Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column( columnDefinition="DATETIME")
    private Date checkout;
    private Status status;
    private String QRCode;
    private String specialrequests;

}
