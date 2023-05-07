package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPet;
    @NotNull(message = "that field cannot be null")
    @Size(min = 2, max = 50, message = "The field must be between {min} and {max} characters long")
    private String name;
    @NotNull(message = "that field cannot be null")
    @Size(min = 2, max = 50, message = "The field must be between {min} and {max} characters long")
    private String specie;
    @NotNull(message = "that field cannot be null")
    private Gender gender;
    private Float weight;
    @NotNull(message = "that field cannot be null")
    @Size(min = 2, max = 50, message = "The field must be between {min} and {max} characters long")
    private String color;
    @NotNull(message = "that field cannot be null")
    @Size(min = 2, max = 50, message = "The field must be between {min} and {max} characters long")
    private String picture;
    @NotNull(message = "that field cannot be null")
    private Situation situation;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pet", orphanRemoval = true)
    @JsonIgnoreProperties("pet")
    private Set<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private Adoption adoption;

    @ManyToMany
    @JsonIgnoreProperties("pet")
    private List<Training> trainings;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pet")
    private List<Reservation> reservations;
}
