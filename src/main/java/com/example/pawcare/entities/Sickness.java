package com.example.pawcare.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Sickness implements Serializable {

    @OneToOne
    private Library library;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSickness;
    private String symptoms;
    private String treatment;
    private String prevention;
}
