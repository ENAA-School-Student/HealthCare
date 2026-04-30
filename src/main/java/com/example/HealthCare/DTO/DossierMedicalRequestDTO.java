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
public class DossierMedicalRequestDTO {

    @NotBlank(message = "diagnostic is mandatory")
    private String diagnostic;
    @NotBlank(message = "observations is mandatory")
    private String observations;
    @NotBlank(message = "dateCreation is mandatory")
    private LocalDate dateCreation;
    @NotBlank(message = "patientId is mandatory")
    private int patientId;
}
