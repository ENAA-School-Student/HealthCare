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
public class CreateMedecinDTO {
    @NotBlank
    private String nom;
    @NotBlank
    private String telephone;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String specialite;
}
