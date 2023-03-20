package com.example.pawcare.entities;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class User implements Serializable {

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Item> items;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private List<Library> libraries;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Appointment> appointments;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String associationName;
    private String email;
    private String password;
    private int phoneNumber;
    private String image;

}
