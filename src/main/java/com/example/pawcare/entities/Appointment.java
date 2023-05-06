package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment implements Serializable{
    @JsonIgnoreProperties("appointments")
    @ManyToOne
    private Pet pet;
    @ManyToOne
    private User user;
    @ManyToOne
    private User doctor;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "start_date", columnDefinition="DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "end_date", columnDefinition="DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;

    @NotNull(message = "Reason date cannot be null")
    private Reason reason;
    @NotNull(message = "Location date cannot be null")
    private Location location;
    private Status status;
    @Size(max = 1000, message = "Notes cannot be longer than {max} characters")
    private String notes;

    private Float prix;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
