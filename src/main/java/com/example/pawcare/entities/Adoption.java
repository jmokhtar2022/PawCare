package com.example.pawcare.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Adoption implements Serializable {

    @OneToOne
    @JsonIgnore
    private Pet pet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdoption;
    private String title;
    private String description;
    private int nbDemande;

    private int nbLikes;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate cDate;
    private String location;
    private String picture;
    private String email;

    @OneToMany(mappedBy = "adoption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentAdoption> comment = new ArrayList<>();


    public Adoption() {
    }
}
