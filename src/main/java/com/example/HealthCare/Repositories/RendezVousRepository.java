package com.example.HealthCare.Repositories;

import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RendezVousRepository extends JpaRepository<RenderVous,Integer> {

    RenderVous findByPatient_Id(int patientId);
    RenderVous findByMedecine_Id(int medecineId);


}
