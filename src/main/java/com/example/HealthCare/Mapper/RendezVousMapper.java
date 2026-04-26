package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.CreateRendezVousDTO;
import com.example.HealthCare.DTO.RendezVousDTO;
import com.example.HealthCare.Models.RenderVous;
import org.mapstruct.Mapper;

@Mapper
public interface RendezVousMapper {
    RendezVousDTO toDto(RenderVous renderVous);
    RenderVous toEntity(CreateRendezVousDTO rendezVousDTO);
}
