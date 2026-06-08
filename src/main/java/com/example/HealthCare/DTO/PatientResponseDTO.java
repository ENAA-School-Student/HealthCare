package com.example.HealthCare.DTO;

import com.example.HealthCare.Models.DossierMedical;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO implements Serializable {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private int totalRendezVous;
    private DossierMedicalDTO dossierMedical;

}
