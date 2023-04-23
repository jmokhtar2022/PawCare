package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Duration is required")
    private long duration;
    @NotBlank(message = "Price is required")
    private float price;
    @NotBlank(message = "Number of places is required")
    private int nbrplaces;
    private Type type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate cDate;
    private String description;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportTraining> reports= new ArrayList<>();







}

