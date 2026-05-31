package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @PostMapping("/AjouterUnPatient")
    public void ajouterPatient(@RequestBody @Valid PatientRequestDTO patientDTO){
        patientService.ajouterPatient(patientDTO);
    }

    @GetMapping("/listerLesPatientsByNom")
    public Page<PatientResponseDTO> getPatientByUserNameAsc(@RequestParam String nom,
                                                            @RequestParam int page,
                                                            @RequestParam int size)
    {
        return patientService.findByUserName(nom,page,size);
    }


    @GetMapping("/listerLesPatientsPagination")
    public Page<PatientResponseDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return patientService.findAll(page,size);
    }



    @GetMapping("/listerLesPatientsPagination")
    public Page<PatientResponseDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return patientService.findAll(page,size);
    }



    @GetMapping("/listerLesPatients")
    public List<PatientResponseDTO> listerPatient(){
        return patientService.listerPatients();
    }

    @GetMapping("/consulterPatientPar{id}")
    public PatientResponseDTO consulterPatientParId(@PathVariable int id){
        return patientService.consulterPatient(id);
    }

    @PutMapping("/modifierUnPatient/{id}")
    public void modifierPatient(@PathVariable int id , @RequestBody @Valid PatientRequestDTO patientDTO){
        patientService.modifierPatient(id,patientDTO);
    }

    @DeleteMapping("/supprimerUnPatient{id}")
    public void supprimerPatient(@PathVariable int id){
        patientService.supprimerPatient(id);
    }
}
