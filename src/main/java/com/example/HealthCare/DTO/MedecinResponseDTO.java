package com.example.HealthCare.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedecinResponseDTO {

    private int id;
    private String nom;
    private String telephone;
    private String email;
    private String specialite;
    private int totalRendezVous;
}
