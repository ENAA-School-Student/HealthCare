package com.example.HealthCare.Controllers;

import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import com.example.HealthCare.Services.RendezVousService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/listerLesRendezVousPagination")
    public Page<RendezVousResponseDTO> getAllProducts(
            @RequestParam int page,
            @RequestParam int size) {
        return rendezVousService.findAll(page,size);
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


    @GetMapping("/mes-rendez-vous")
    public List<RendezVousResponseDTO> getMesRendezVous() {
        return rendezVousService.getMesRendezVous();
    }
}

