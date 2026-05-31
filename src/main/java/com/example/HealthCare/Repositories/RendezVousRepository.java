package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import com.example.HealthCare.Models.RenderVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RenderVous,Integer> {
    List<RenderVous> findByPatient_Id(int patientId);
    List<RenderVous> findByMedecine_Id(int medecineId);

    Page<RenderVous> findRenderVousByStatut(RendezVousStatutEnum statut, Pageable pageable);
}
