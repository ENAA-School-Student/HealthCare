package com.example.HealthCare.DTO;

import com.example.HealthCare.Enums.RendezVousStatutEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "dateRendezVous is mandatory")
    private LocalDate dateRendezVous;
    private RendezVousStatutEnum statut;
    @NotNull(message = "medecinId is mandatory")
    @Positive(message = "medecinId should be positive")
    private int medecinId;
    @NotNull(message = "patientId is mandatory")
    @Positive(message = "patientId should be positive")
    private int patientId;
}
