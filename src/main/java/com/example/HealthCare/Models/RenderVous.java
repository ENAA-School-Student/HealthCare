package com.example.HealthCare.Models;

import com.example.HealthCare.Enums.RendezVousStatutEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private RendezVousStatutEnum statut;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "medecine_id")
    private Medecine medecine;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
