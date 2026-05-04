package com.example.HealthCare.DTO;

import com.example.HealthCare.Models.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedicalDTO {
    private int id;
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;
 
}
