package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.CreateDossieMedicalDTO;
import com.example.HealthCare.DTO.CreateMedecinDTO;
import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.DTO.RendezVousDTO;
import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Services.DossierMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

@RestController
@RequestMapping("/api/dossierMedical")
public class DossierMedicalController {

    @Autowired
    private DossierMedicalService dossierMedicalService;


    @PostMapping("/creeUnDossierMedical")
    public void ajouterDossierMedical(@RequestBody CreateDossieMedicalDTO dossieMedicalDTO){
        dossierMedicalService.ajouterDossierMedicalPourPatient(dossieMedicalDTO);
    }


    @PostMapping("/ajouterDiagnostic/{id}")
    public DossierMedicalDTO ajouterDiagnostic(@PathVariable int id , @RequestBody String diagnostic){
       return dossierMedicalService.ajouterDiagnostic(id, diagnostic);
    }

    @PostMapping("/ajouterObservations/{id}")
    public DossierMedicalDTO ajouterObservations(@PathVariable int id , @RequestBody String Observations){
        return dossierMedicalService.ajouterObservations(id, Observations);
    }


    @GetMapping("/consulterDossierMedical/{id}")
    public DossierMedicalDTO consulterDossierMedical(@PathVariable int id){
        return dossierMedicalService.consulterUnDossierMedical(id);
    }



}
