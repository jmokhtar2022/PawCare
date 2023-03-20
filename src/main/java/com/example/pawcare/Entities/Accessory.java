package com.example.pawcare.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Accessory implements Serializable {

    @ManyToMany(mappedBy = "accessories", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cart> carts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccessory;
    private String name;
    private float price;
    private String description;
    private String image;

}
