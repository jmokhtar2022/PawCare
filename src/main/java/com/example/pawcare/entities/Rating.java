package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Min(1)
    @Max(5)
    private int ratingValue;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column( columnDefinition="DATETIME")
    private Date date;
    @ManyToOne
    private Hotel hotel;

}