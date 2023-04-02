package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"appointment"})
public class Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPet;
    private String name;
    private String specie;
    private Gender gender;
    private Float weight;
    private String color;
    private String picture;
    private Situation situation;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet",orphanRemoval = true)
    private Appointment appointment;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private Adoption adoption;

    @ManyToMany
    private List<Training> trainings;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private List<Reservation> reservations;
}
