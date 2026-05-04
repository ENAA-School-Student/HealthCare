package com.example.HealthCare.DTO;

import com.example.HealthCare.Enums.RendezVousStatutEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "statut is mandatory")
    private RendezVousStatutEnum statut;
    @NotNull(message = "medecinId is mandatory")
    private int medecinId;
    @NotNull(message = "patientId is mandatory")
    private int patientId;
}
