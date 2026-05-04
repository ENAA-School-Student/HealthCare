package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Interfaces.MedecinRendezVousCount;
import com.example.HealthCare.Models.Patient;
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
//  List<RenderVous> rendezVousByMedecinAndDate(int medcinId,LocalDate date);
    //SQL
   @Query("select rendezVous From RenderVous rendezVous where rendezVous.dateRendezVous = ?1 and rendezVous.medecine.id = ?2")
   List<RenderVous> rendezVousByMedecinAndDate(LocalDate date,int medcinId);

    @Query("select rendezVous From RenderVous rendezVous where rendezVous.patient.id = ?1 and rendezVous.dateRendezVous = ?2")
    List<RenderVous> rendezVousByPatientAndDate(int patientId , LocalDate date);













    @Query (value ="select * From render_vous rv where rv.patient_id= ? ",nativeQuery = true)
    List<RenderVous> renderVousParPatient(int id);

    @Query("Select render_vous from RenderVous render_vous where render_vous.patient.id = :id ")
    List<RenderVous> renderVousParPatietn(int id);


@Query(value = "select m.id,m.nom,COUNT(r.id) as total_rendez_vous From medecine m LEFT JOIN render_vous r on r.medecine_id = m.id GROUP BY m.id, m.nom ORDER BY total_rendez_vous DESC " ,nativeQuery = true)
    List<MedecinRendezVousCount> medecinRendezVousCount();

@Query(value = "select p.* , COUNT(r.id) as total_rendezVous FROM patient p Left Join render_vous r ON r.patient_id = p.id GROUP BY p.id HAVING total_rendezVous > ?  ",nativeQuery = true )
    List<Patient> patientRendezVous(int number);


}
