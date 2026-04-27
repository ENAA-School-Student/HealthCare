package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.CreateDossieMedicalDTO;
import com.example.HealthCare.DTO.CreateMedecinDTO;
import com.example.HealthCare.DTO.RendezVousDTO;
import com.example.HealthCare.Services.DossierMedicalService;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

@RestController
@RequestMapping("/api/dossierMedical")
public class DossierMedicalController {

    private final DossierMedicalService dossierMedicalService;

    public DossierMedicalController(DossierMedicalService dossierMedicalService) {
        this.dossierMedicalService = dossierMedicalService;
    }


    @PostMapping
    public void ajouterDossierMedical(@RequestBody CreateDossieMedicalDTO dossieMedicalDTO){
        dossierMedicalService.ajouterDossierMedicalPourPatient(dossieMedicalDTO);
    }


}
