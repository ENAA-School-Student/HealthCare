package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.*;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RenderVousServiceTest {


    @Autowired
    private RendezVousService rendezVousService;
    @Autowired
    private MedecinService medecinService;
    @Autowired
    private PatientService patientService;

    RendezVousRequestDTO rendezVousRequestDTO;
    RendezVousRequestDTO rendezVousRequestTwo;
    MedecineRequestDTO medecineRequestDTO;
    PatientRequestDTO patientRequestDTO;
    PatientResponseDTO patientResponseDTO;
    MedecinResponseDTO medecinResponseDTO;

    @BeforeEach
    public void setUp(){
        medecineRequestDTO = new MedecineRequestDTO("sami","12121","hadi@gmail.com","doctor");
        medecinResponseDTO = medecinService.ajouterMedecine(medecineRequestDTO);


        patientRequestDTO = new PatientRequestDTO("ayoub","hadi","ayoub120@gmail.com","5754578",LocalDate.parse("2020-12-12"));
        patientResponseDTO = patientService.ajouterPatient(patientRequestDTO);

        rendezVousRequestDTO = new RendezVousRequestDTO();
        rendezVousRequestDTO.setStatut(RendezVousStatutEnum.CONFIRME);
        rendezVousRequestDTO.setMedecinId(medecinResponseDTO.getId());
        rendezVousRequestDTO.setPatientId(patientResponseDTO.getId());
        rendezVousRequestDTO.setDateRendezVous(LocalDate.parse("2020-12-01"));
        rendezVousRequestTwo = new RendezVousRequestDTO(LocalDate.parse("2021-12-01"),RendezVousStatutEnum.CONFIRME,medecinResponseDTO.getId(),patientResponseDTO.getId());

    }
    @Test
    void creeRendezVous() {
        RendezVousResponseDTO save = rendezVousService.creeRendezVous(rendezVousRequestDTO);
        assertNotNull(save);
        assertEquals("availble",save.getStatut());
        assertEquals(medecinResponseDTO.getId() , save.getMedecine().getId());
        assertEquals(patientResponseDTO.getId() , save.getPatient().getId());
    }

    @Test
    void listerRendezVous() {
        List<RendezVousResponseDTO> rendezVousResponseDTOS = rendezVousService.listerRendezVous();
        assertNotNull(rendezVousResponseDTOS);
        assertTrue(rendezVousResponseDTOS.size() > 1);

    }

    @Test
    void annulerRendezVous() {
        RendezVousResponseDTO rendezVous = rendezVousService.creeRendezVous(rendezVousRequestDTO);
        assertNotNull(rendezVous);
        RendezVousResponseDTO responseDTO = rendezVousService.annulerRendezVous(rendezVous.getId());
        assertEquals(RendezVousStatutEnum.ANNULE,responseDTO.getStatut());
    }

    @Test
    void findRendezVousByMedecin() {
        rendezVousService.creeRendezVous(rendezVousRequestDTO);
        rendezVousService.creeRendezVous(rendezVousRequestTwo);
        List<RendezVousResponseDTO> rendezVousResponseDTOS = rendezVousService.findRendezVousByMedecin(medecinResponseDTO.getId());
        assertNotNull(rendezVousResponseDTOS);
        assertFalse(rendezVousResponseDTOS.isEmpty());
        assertTrue(rendezVousResponseDTOS.size() == 2);

    }

    @Test
    void findRendezVousByPatient() {
        rendezVousService.creeRendezVous(rendezVousRequestDTO);
        rendezVousService.creeRendezVous(rendezVousRequestTwo);
        List<RendezVousResponseDTO> rendezVousResponseDTOS = rendezVousService.findRendezVousByPatient(patientResponseDTO.getId());
        assertNotNull(rendezVousResponseDTOS);
        assertFalse(rendezVousResponseDTOS.isEmpty());
        assertTrue(rendezVousResponseDTOS.size() == 2);
    }

    @Test
    void modifierRendezVous() {
        RendezVousResponseDTO save = rendezVousService.creeRendezVous(rendezVousRequestDTO);
        RendezVousRequestDTO requestDTO =  new RendezVousRequestDTO(LocalDate.parse("2011-12-01"),RendezVousStatutEnum.EN_ATTENTE,medecinResponseDTO.getId(),patientResponseDTO.getId());
        RendezVousResponseDTO editedRendezVous = rendezVousService.modifierRendezVous(save.getId(),requestDTO);
        assertEquals(RendezVousStatutEnum.EN_ATTENTE ,editedRendezVous.getStatut());
    }
}