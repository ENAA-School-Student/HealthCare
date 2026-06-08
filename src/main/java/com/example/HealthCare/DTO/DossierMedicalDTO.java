package com.example.HealthCare.DTO;

import com.example.HealthCare.Models.Patient;
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
public class DossierMedicalDTO implements Serializable {
    private int id;
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;
 
}
