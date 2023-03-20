package com.example.pawcare.entities;

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

    @ManyToMany
    private List<Accessory> accessories;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;
    private float totalCart;
}
