package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.CreatePatientDTO;
import com.example.HealthCare.DTO.PatientDTO;
import com.example.HealthCare.Models.Patient;
import org.mapstruct.Mapper;

import java.util.regex.Pattern;

@Mapper(componentModel = "spring")
public interface PatientMapper {
PatientDTO toDto(Patient patient);
Patient toEntity(CreatePatientDTO patientDTO);
}
