package com.example.HealthCare.DTO;

import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRendezVousDTO {
    @NotBlank
    private LocalDate dateRendezVous;
    @NotBlank
    private String statut;
    @NotBlank
    private int medecinId;
    @NotBlank
    private int patientId;
}
