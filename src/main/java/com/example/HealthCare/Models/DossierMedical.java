package com.example.HealthCare.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DossierMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
