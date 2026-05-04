package com.example.HealthCare.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class DossierMedicalRequestDTO {

    @NotBlank(message = "diagnostic is mandatory")
    private String diagnostic;
    @NotBlank(message = "observations is mandatory")
    private String observations;
    @NotNull(message = "dateCreation is mandatory")
    @PastOrPresent(message = "dateCreation cannot be in the future")
    private LocalDate dateCreation;
    @NotNull(message = "patientId is mandatory")
    @Positive(message = "patientId must be positive")
    private int patientId;
}
