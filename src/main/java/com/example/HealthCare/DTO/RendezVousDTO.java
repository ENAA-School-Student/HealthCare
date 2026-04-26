package com.example.HealthCare.DTO;

import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDTO {
    private LocalDate dateRendezVous;
    private String statut;
    private Medecine medecine;
    private Patient patient;
}
