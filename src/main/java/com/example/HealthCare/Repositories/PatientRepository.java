package com.example.HealthCare.Repositories;

import com.example.HealthCare.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    void deletePatientById(int id);
}
