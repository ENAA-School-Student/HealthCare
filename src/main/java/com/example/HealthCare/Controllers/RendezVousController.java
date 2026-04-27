package com.example.HealthCare.Controllers;

import com.example.HealthCare.DTO.CreateRendezVousDTO;
import com.example.HealthCare.DTO.RendezVousDTO;
import com.example.HealthCare.Models.RenderVous;
import com.example.HealthCare.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendezVous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;


    @PostMapping("/creeUnRendezVous")
    public void creeRendezVous(@RequestBody CreateRendezVousDTO rendezVousDTO){
        rendezVousService.creeRendezVous(rendezVousDTO);
    }


    @GetMapping("/listerLesRendezVous")
    public List<RendezVousDTO> listerDossierMedical(){
        return rendezVousService.listerRendezVous();
    }


    @PatchMapping("/annulerRendezVous/{id}")
    public RenderVous annulerRendezVous(@PathVariable int id){
      return  rendezVousService.annulerRendezVous(id);
    }


    @GetMapping("/findRendezVousByMedecine{id}")
    public RendezVousDTO findRendezVousByMedcine(@PathVariable int id){
        return rendezVousService.findRendezVousByMedecin(id);
    }


    @GetMapping("/findRendezVousByPatient{id}")
    public RendezVousDTO findRendezVousByPatient(@PathVariable int id){
        return rendezVousService.findRendezVousByPatient(id);
    }


    @PutMapping("/modifierRendezVousById/{id}")
    public void modifierRendezVous(@PathVariable int id , @RequestBody CreateRendezVousDTO rendezVousDTO){
        rendezVousService.modifierRendezVous(id , rendezVousDTO);
    }
}
