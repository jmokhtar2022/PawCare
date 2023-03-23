package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private LocalDateTime cDate;
    private String description;







}

