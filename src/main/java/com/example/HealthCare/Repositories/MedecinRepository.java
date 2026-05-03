package com.example.HealthCare.Repositories;

import com.example.HealthCare.Models.Medecine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecine,Integer> {



    @Query("SELECT m, COUNT(r) FROM Medecine m LEFT JOIN RenderVous r ON r.medecine.id = m.id GROUP BY m.nom")
    List<Object[]> findByMedcinNumberRendezVous();


}
