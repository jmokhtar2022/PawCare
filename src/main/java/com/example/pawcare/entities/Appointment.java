package com.example.pawcare.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment implements Serializable{
    @OneToOne(optional = true)
    private Pet pet;
    @ManyToOne
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "start_date", columnDefinition="DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "end_date", columnDefinition="DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;
    private Reason reason;
    private Location location;
    private Status status;
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
