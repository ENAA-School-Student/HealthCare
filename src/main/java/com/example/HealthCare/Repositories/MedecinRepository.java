package com.example.HealthCare.Repositories;

import com.example.HealthCare.Models.Medecine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecine,Integer> {
}
