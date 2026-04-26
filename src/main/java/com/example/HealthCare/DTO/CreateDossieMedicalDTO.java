package com.example.HealthCare.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDossieMedicalDTO {
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;
    private int patientId;
}
