package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.DossierMedicalRequestDTO;
import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.Models.DossierMedical;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DossierMedicalMapper {
    @Mapping(source = "patientId" , target = "patient.id")
    DossierMedical toEntity(DossierMedicalRequestDTO dossierMedicalDTO);
    DossierMedicalDTO toDto(DossierMedical dossierMedical);



}
