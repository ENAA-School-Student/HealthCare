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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rendezVous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @GetMapping("/download/patient/{id}")
    public ResponseEntity<InputStreamResource> downloadRendezVousByPatient(@PathVariable int id) {
        ByteArrayInputStream bis = rendezVousService.exportRendezVousByPatientToPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=rendez_vous_patient_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/download/mes-rendez-vous")
    public ResponseEntity<InputStreamResource> downloadMesRendezVous() {
        ByteArrayInputStream bis = rendezVousService.exportMesRendezVousToPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=mes_rendez_vous.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


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

