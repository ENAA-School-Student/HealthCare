package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Models.RenderVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RenderVous,Integer> {

    List<RenderVous> findByPatient_Id(int patientId);
   List<RenderVous> findByMedecine_Id(int medecineId);


}
