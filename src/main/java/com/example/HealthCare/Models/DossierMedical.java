package com.example.HealthCare.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    private int id;
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
