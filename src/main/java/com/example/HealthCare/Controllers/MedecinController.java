package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.CreateMedecinDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.Services.MedecinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;


    @GetMapping("/ListerMedecines")
    public List<MedecinDTO> afficherMedecins(){
      return medecinService.listerMedecine();
    }


    @PostMapping("/AjouterMedecine")
    public void ajouterMedecine(@RequestBody @Valid CreateMedecinDTO medecinDTO){
        medecinService.ajouterMedecine(medecinDTO);
    }

    @DeleteMapping("/supprimerMedecine/{id}")
    public void supprimerMedecine(@PathVariable int id){
        medecinService.supprimerMedecine(id);
    }


    @PutMapping("/modifierMedecine/{id}")
        public void modifierMedecine(@PathVariable int id , @RequestBody @Valid CreateMedecinDTO medecinDTO){
            medecinService.modifierMedecine(id,medecinDTO);
    }

}
