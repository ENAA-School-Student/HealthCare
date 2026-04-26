package com.example.HealthCare.Controllers;


import com.example.HealthCare.DTO.CreateMedecinDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.Services.MedecinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    private final MedecinService medecinService;


    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }


    @GetMapping
    public List<MedecinDTO> afficherMedecins(){
      return medecinService.listerMedecin();
    }


    @PostMapping
    public void ajouterMedecin(@RequestBody CreateMedecinDTO medecinDTO){
        medecinService.ajouterMedecin(medecinDTO);
    }

    @DeleteMapping("/{id}")
    public void supprimerMedecin(@PathVariable int id){
        medecinService.supprimerMedecin(id);
    }


    @PutMapping("/{id}")
        public void modifierMedecin(@PathVariable int id , @RequestBody CreateMedecinDTO medecinDTO){
            medecinService.modifierMedecin(id,medecinDTO);
    }

}
