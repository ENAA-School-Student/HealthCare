package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.UserEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    void deletePatientById(int id);

    Page<Patient> findAll(Pageable pageable);

    Page<Patient> findPatientsByDateNaissance(LocalDate date , Pageable pageable);

    Optional<Patient> findByUser(UserEntity user);


}
