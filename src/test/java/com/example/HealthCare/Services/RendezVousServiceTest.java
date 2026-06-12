package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.*;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RendezVousServiceTest {

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
    void setUp() {
        medecineRequestDTO = new MedecineRequestDTO("sami", "12121", "hadi@gmail.com", "doctor");
        medecinResponseDTO = medecinService.ajouterMedecine(medecineRequestDTO);

        patientRequestDTO = new PatientRequestDTO("ayoub", "hadi", "ayoub120@gmail.com", "5754578", LocalDate.parse("2020-12-12"));
        patientResponseDTO = patientService.ajouterPatient(patientRequestDTO);

        rendezVousRequestDTO = new RendezVousRequestDTO();
        rendezVousRequestDTO.setStatut(RendezVousStatutEnum.CONFIRME);
        rendezVousRequestDTO.setMedecinId(medecinResponseDTO.getId());
        rendezVousRequestDTO.setPatientId(patientResponseDTO.getId());
        rendezVousRequestDTO.setDateRendezVous(LocalDate.parse("2020-12-01"));

        rendezVousRequestTwo = new RendezVousRequestDTO(LocalDate.parse("2021-12-01"), RendezVousStatutEnum.CONFIRME, medecinResponseDTO.getId(), patientResponseDTO.getId());
    }

    @Test
    void creeRendezVous_savesAndReturnsDTO() {
        RendezVousResponseDTO saved = rendezVousService.creeRendezVous(rendezVousRequestDTO);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(medecinResponseDTO.getId(), saved.getMedecine().getId());
        assertEquals(patientResponseDTO.getId(), saved.getPatient().getId());
        assertEquals(LocalDate.parse("2020-12-01"), saved.getDateRendezVous());
    }



    @Test
    void annulerRendezVous_setsStatutToAnnule() {
        RendezVousResponseDTO saved = rendezVousService.creeRendezVous(rendezVousRequestDTO);

        RendezVousResponseDTO cancelled = rendezVousService.annulerRendezVous(saved.getId());

        assertNotNull(cancelled);
        assertEquals(RendezVousStatutEnum.ANNULE, cancelled.getStatut());
    }

    @Test
    void findRendezVousByMedecin_returnsAllRendezVousForMedecin() {
        rendezVousService.creeRendezVous(rendezVousRequestDTO);
        rendezVousService.creeRendezVous(rendezVousRequestTwo);

        List<RendezVousResponseDTO> result = rendezVousService.findRendezVousByMedecin(medecinResponseDTO.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getMedecine().getId() == medecinResponseDTO.getId()));
    }

    @Test
    void findRendezVousByPatient_returnsAllRendezVousForPatient() {
        rendezVousService.creeRendezVous(rendezVousRequestDTO);
        rendezVousService.creeRendezVous(rendezVousRequestTwo);

        List<RendezVousResponseDTO> result = rendezVousService.findRendezVousByPatient(patientResponseDTO.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getPatient().getId() == patientResponseDTO.getId()));
    }

    @Test
    void modifierRendezVous_updatesStatutAndDate() {
        RendezVousResponseDTO saved = rendezVousService.creeRendezVous(rendezVousRequestDTO);
        RendezVousRequestDTO updateRequest = new RendezVousRequestDTO(LocalDate.parse("2011-12-01"), RendezVousStatutEnum.EN_ATTENTE, medecinResponseDTO.getId(), patientResponseDTO.getId());

        RendezVousResponseDTO updated = rendezVousService.modifierRendezVous(saved.getId(), updateRequest);

        assertEquals(saved.getId(), updated.getId());
        assertEquals(RendezVousStatutEnum.EN_ATTENTE, updated.getStatut());
        assertEquals(LocalDate.parse("2011-12-01"), updated.getDateRendezVous());
    }
}