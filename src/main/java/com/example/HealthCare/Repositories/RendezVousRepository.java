package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Models.RenderVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RenderVous,Integer> {

    List<RenderVous> findByPatient_Id(int patientId);
   List<RenderVous> findByMedecine_Id(int medecineId);



   //JPQL
    //@Query(value = "SELECT * FROM render_vous where medecine_id = ? and date_rendez_vous = ?" , nativeQuery = true)
    //List<RenderVous> rendezVousByMedecinAndDate(int medcinId,LocalDate date);
    //SQL
   @Query("select rendezVous From RenderVous rendezVous where rendezVous.dateRendezVous = ?1 and rendezVous.medecine.id = ?2")
   List<RenderVous> rendezVousByMedecinAndDate(LocalDate date,int medcinId);


@Query("select rendezVous From RenderVous rendezVous where rendezVous.patient.id = ?1 and rendezVous.dateRendezVous = ?2")
    List<RenderVous> rendezVousByPatientAndDate(int patientId , LocalDate date);






}
