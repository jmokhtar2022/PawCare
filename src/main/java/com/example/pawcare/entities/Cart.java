package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart implements Serializable {
    @OneToOne
    private Item item;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Accessory> accessories;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;
    private float totalCart;
}
