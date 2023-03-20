package com.example.pawcare.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Role implements Serializable {

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Roles roleName;
}
