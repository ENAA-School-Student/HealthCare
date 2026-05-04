package com.example.HealthCare.Controllers;

import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Interfaces.MedecinRendezVousCount;
import com.example.HealthCare.Services.RendezVousService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rendezVous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;


    @PostMapping("/creeUnRendezVous")
    public void creeRendezVous(@RequestBody @Valid RendezVousRequestDTO rendezVousDTO){
        rendezVousService.creeRendezVous(rendezVousDTO);
    }


    @GetMapping("/listerLesRendezVous")
    public List<RendezVousResponseDTO> listerDossierMedical(){
        return rendezVousService.listerRendezVous();
    }


    @PatchMapping("/annulerRendezVous/{id}")
    public RendezVousResponseDTO annulerRendezVous(@PathVariable int id){
      return  rendezVousService.annulerRendezVous(id);
    }


    @GetMapping("/findRendezVousByMedecine{id}")
    public List<RendezVousResponseDTO>  findRendezVousByMedcine(@PathVariable int id){
        return  rendezVousService.findRendezVousByMedecin(id);
    }


    @GetMapping("/findRendezVousByPatient{id}")
    public List<RendezVousResponseDTO> findRendezVousByPatient(@PathVariable int id){
        return rendezVousService.findRendezVousByPatient(id);
    }


    @PutMapping("/modifierRendezVousById/{id}")
    public void modifierRendezVous(@PathVariable int id , @RequestBody @Valid RendezVousRequestDTO rendezVousDTO){
        rendezVousService.modifierRendezVous(id , rendezVousDTO);
    }


    @GetMapping("/getRendezVousByDateAndMedecinId")
    public List<RendezVousResponseDTO> getRendezVousByDateAndMedecinId(@RequestParam int id, @RequestParam LocalDate date){
        return rendezVousService.findRendezVousByGivinDateAndMedecinId(date,id);
    }

    @GetMapping("/getRendezVousByDateAndPatientId")
    public List<RendezVousResponseDTO> getRendezVousByDateAndPatientId(@RequestParam int id, @RequestParam LocalDate date){
        return rendezVousService.findRendezVousByGivinDateAndPatientId(date,id);
    }

    @GetMapping("/rendezVous")
    public List<RendezVousResponseDTO> rendervoudParpatietn(@RequestParam int id){
        return rendezVousService.findrenderVousParPatient(id);
    }

    @GetMapping("/countRendezVousParMedecen")
    public List<MedecinRendezVousCount> medecinRendezVousCounts (){
        return rendezVousService.getMedecenTotalRendezVous();
    }
    @GetMapping("/RendezVousParPatietnGreaterThan")
    public List<PatientResponseDTO> patientResponseDTOS (@RequestParam int number){
        return rendezVousService.getPatientTotalRendezVousGretaerthan(number);
    }

}
