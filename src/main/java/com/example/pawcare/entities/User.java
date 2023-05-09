package com.example.pawcare.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;
    private String firstName;
    private String lastName;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;



    @NotBlank
    @Size(max = 120)
    private String password;


    private int phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Appointment> appointments;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "doctor", orphanRemoval = true)
    private Set<Appointment> apt_doctors;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Item> items;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private List<Library> libraries;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Article> articles;

    public User() {
    }



    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String firstname, String lastname, String username, String email, String password, int phone) {
        this.firstName = firstname;
        this.lastName= lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone= phone;

    }
    public User(String username, String email, String password, int phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone= phone;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}