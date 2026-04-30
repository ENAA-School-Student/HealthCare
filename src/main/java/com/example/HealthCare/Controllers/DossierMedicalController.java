package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.DossierMedicalRequestDTO;
import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.Services.DossierMedicalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dossierMedical")
public class DossierMedicalController {

    @Autowired
    private DossierMedicalService dossierMedicalService;


    @PostMapping("/creeUnDossierMedical")
    public void ajouterDossierMedical(@RequestBody @Valid DossierMedicalRequestDTO dossieMedicalDTO){
        dossierMedicalService.ajouterDossierMedicalPourPatient(dossieMedicalDTO);
    }


    @PostMapping("/ajouterDiagnostic/{id}")
    public DossierMedicalDTO ajouterDiagnostic(@PathVariable int id , @Valid @RequestBody String diagnostic){
       return dossierMedicalService.ajouterDiagnostic(id, diagnostic);
    }

    @PostMapping("/ajouterObservations/{id}")
    public DossierMedicalDTO ajouterObservations(@PathVariable int id ,@Valid @RequestBody String Observations){
        return dossierMedicalService.ajouterObservations(id, Observations);
    }


    @GetMapping("/consulterDossierMedical/{id}")
    public DossierMedicalDTO consulterDossierMedical(@PathVariable int id){
        return dossierMedicalService.consulterUnDossierMedical(id);
    }



}
