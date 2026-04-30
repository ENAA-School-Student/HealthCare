package com.example.HealthCare.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousResponseDTO {
    private LocalDate dateRendezVous;
    private String statut;
    private MedecinResponseDTO medecine;
    private PatientResponseDTO patient;
}
