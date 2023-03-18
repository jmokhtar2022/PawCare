package com.example.pawcare.Entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Reason reason;
    private Location Location;
    private Status status;
    private String notes;
}
