package com.example.HealthCare.DTO;

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
public class RendezVousRequestDTO {
    @NotBlank(message = "dateRendezVous is mandatory")
    private LocalDate dateRendezVous;
    @NotBlank(message = "statut is mandatory")
    private String statut;
    @NotBlank(message = "medecinId is mandatory")
    private int medecinId;
    @NotBlank(message = "patientId is mandatory")
    private int patientId;
}
