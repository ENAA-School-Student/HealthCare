package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.MedecineRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.Services.MedecinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;


    @GetMapping("/ListerMedecines")
    public Page<MedecinResponseDTO> afficherMedecins(@RequestParam Pageable pageable){
      return medecinService.listerMedecine(pageable);
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
