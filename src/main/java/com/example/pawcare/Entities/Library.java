package com.example.pawcare.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Library implements Serializable {

    @OneToOne(mappedBy = "library", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Sickness sickness;

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle;
    private String content;

}
