package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Models.Patient;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    void deletePatientById(int id);
    Page<Patient> findByNom(String nom, Pageable pageable);
    Page<Patient> findAll(Pageable pageable);
}
