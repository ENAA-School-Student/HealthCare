package com.example.HealthCare.DTO;

import com.example.HealthCare.Enums.RendezVousStatutEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousResponseDTO implements Serializable {
    private int id;
    private LocalDate dateRendezVous;
    private RendezVousStatutEnum statut;
    private MedecinResponseDTO medecine;
    private PatientResponseDTO patient;
}
