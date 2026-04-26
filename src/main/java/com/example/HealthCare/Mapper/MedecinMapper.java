package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.CreateMedecinDTO;
import com.example.HealthCare.DTO.CreatePatientDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.DTO.PatientDTO;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    MedecinDTO toDto(Medecine medecine);
    Medecine toEntity(CreateMedecinDTO medecinDTO);
}
