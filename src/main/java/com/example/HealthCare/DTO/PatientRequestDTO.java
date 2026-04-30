package com.example.HealthCare.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {
    @NotBlank(message = "nom is mandatory")
    private String nom;
    @NotBlank(message = "prenom is mandatory")
    private String prenom;
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "telephone is mandatory")
    private String telephone;
    @DateTimeFormat
    private LocalDate dateNaissance;
}
