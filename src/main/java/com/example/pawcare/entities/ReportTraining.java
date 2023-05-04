package com.example.pawcare.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class ReportTraining implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long idReport;
        private String message;
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        private Training training;

        // add getter and setter for training field
        public Training getTraining() {
            return training;
        }

        public void setTraining(Training training) {
            this.training = training;
        }
    }


