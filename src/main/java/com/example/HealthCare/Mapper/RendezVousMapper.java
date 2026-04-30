package com.example.HealthCare.Mapper;


import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Models.RenderVous;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {
    RendezVousResponseDTO toDto(RenderVous renderVous);
    RenderVous toEntity(RendezVousRequestDTO rendezVousDTO);
    List<RendezVousResponseDTO> toDtoList(List<RenderVous> renderVousList);


}
