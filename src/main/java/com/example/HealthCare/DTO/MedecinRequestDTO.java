package com.example.HealthCare.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedecinRequestDTO {
    @NotBlank(message = "nom is mandatory")
    private String nom;
    @NotBlank(message = "telephone is mandatory")
    private String telephone;
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "specialite is mandatory")
    private String specialite;
}
