package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.Models.Medecine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecine,Long> {
    Page<Medecine> findMedecineBySpecialiteContaining(String specialite , Pageable pageable);
}
