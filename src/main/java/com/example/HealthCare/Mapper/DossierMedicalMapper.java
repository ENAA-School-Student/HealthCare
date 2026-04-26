package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.CreateDossieMedicalDTO;
import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.Models.DossierMedical;
import org.mapstruct.Mapper;

@Mapper
public interface DossierMedicalMapper {
    DossierMedicalDTO toDto(DossierMedical dossierMedical);
    DossierMedical toEntity(CreateDossieMedicalDTO dossierMedicalDTO);
}
