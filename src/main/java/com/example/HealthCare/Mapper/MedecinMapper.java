package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.MedecinRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.Models.Medecine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    MedecinResponseDTO toDto(Medecine medecine);
    Medecine toEntity(MedecinRequestDTO medecinDTO);
}
