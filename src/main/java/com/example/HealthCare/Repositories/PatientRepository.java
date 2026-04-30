package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    void deletePatientById(int id);
}
