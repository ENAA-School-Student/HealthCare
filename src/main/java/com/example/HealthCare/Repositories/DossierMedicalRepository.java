package com.example.HealthCare.Repositories;

import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Models.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical,Integer> {
    Optional<DossierMedical> findByPatient(Patient patient);
    Page<DossierMedical> findDossierMedicalByDiagnostic(Pageable pageable, String diagnostic);
}