package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Models.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
PatientResponseDTO toDto(Patient patient);
Patient toEntity(PatientRequestDTO patientDTO);
}
