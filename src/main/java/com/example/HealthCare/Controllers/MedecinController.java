package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.MedecineRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Services.MedecinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;


    @GetMapping("/ListerMedecines")
    public List<MedecinResponseDTO> afficherMedecins(){
        return medecinService.listerMedecine();
    }


    @GetMapping("/getMedecinsBySpecialite")
    public Page<MedecinResponseDTO> findMedcinsBySpecialite(@RequestParam String specialite,
                                                            @RequestParam int page,
                                                            @RequestParam int size){
        return medecinService.findBySpecialiteContaining(specialite,page,size);
    }



    @GetMapping("/listerLesMedecinPagination")
    public Page<MedecinResponseDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return medecinService.findAllMedecinPagination(page,size);
    }

    @PostMapping("/AjouterMedecine")
    public void ajouterMedecine(@RequestBody @Valid MedecineRequestDTO medecinDTO){
        medecinService.ajouterMedecine(medecinDTO);
    }

    @DeleteMapping("/supprimerMedecine/{id}")
    public void supprimerMedecine(@PathVariable int id){
        medecinService.supprimerMedecine(id);
    }


    @PutMapping("/modifierMedecine/{id}")
    public void modifierMedecine(@PathVariable int id , @RequestBody @Valid MedecineRequestDTO medecinDTO){
        medecinService.modifierMedecine(id,medecinDTO);
    }

}
