package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Item implements Serializable {
    @JsonIgnore

    @OneToOne
    private Cart cart;

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idItem;
    private LocalDate date;
    private float deliveryPrice;
    private String trackingCode;
    private OrderStatus orderstatus;
    private float totalItem;
}
