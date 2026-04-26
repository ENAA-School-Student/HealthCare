package com.example.HealthCare.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RenderVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateRendezVous;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "medecine_id")
    private Medecine medecine;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
