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

    @GetMapping("/findRendezVousByStatut")
    public Page<RendezVousResponseDTO> findRendezVousByStatut(@RequestParam String statut,
                                                              @RequestParam int page,
                                                              @RequestParam int size)
    {
        RendezVousStatutEnum statutEnum = RendezVousStatutEnum.valueOf(statut.toUpperCase());
        return rendezVousService.findRendezVousByStatutRendezVous(statutEnum,page,size);

    }


    @GetMapping("/listerLesRendezVousPagination")
    public Page<RendezVousResponseDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return rendezVousService.findAll(page,size);
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
}
