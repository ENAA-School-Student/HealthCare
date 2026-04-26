package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.CreatePatientDTO;
import com.example.HealthCare.DTO.PatientDTO;
import com.example.HealthCare.Services.PatientService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @PostMapping
    public void ajouterPatient(@RequestBody CreatePatientDTO patientDTO){
        patientService.ajouterPatient(patientDTO);
    }

    @GetMapping
    public List<PatientDTO> listerPatient(){
        return patientService.listerPatients();
    }


    @PutMapping("/{id}")
    public void modifierPatient(@PathVariable int id , @RequestBody CreatePatientDTO patientDTO){
        patientService.modifierPatient(id,patientDTO);
    }

    @DeleteMapping("/{id}")
    public void supprimerPatient(@PathVariable int id){
        patientService.supprimerPatient(id);
    }
}
