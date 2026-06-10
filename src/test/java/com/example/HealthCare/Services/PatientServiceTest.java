package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    private PatientRequestDTO patientdto;

    @BeforeEach
    void setUp() {
        patientdto = new PatientRequestDTO();
        patientdto.setNom("Hadi");
        patientdto.setPrenom("souhayb");
        patientdto.setEmail("souhaybhadi@gmail.com");
        patientdto.setTelephone("0621421383");
    }

    @Test
    void ajouterPatient_savesAndReturnsDTO() {
        PatientResponseDTO saved = patientService.ajouterPatient(patientdto);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Hadi", saved.getNom());
        assertEquals("souhayb", saved.getPrenom());
        assertEquals("souhaybhadi@gmail.com", saved.getEmail());
        assertEquals("0621421383", saved.getTelephone());
    }

    @Test
    void supprimerPatient_returnsTrueWhenDeleted() {
        PatientResponseDTO saved = patientService.ajouterPatient(patientdto);

        Boolean isDeleted = patientService.supprimerPatient(saved.getId());

        assertTrue(isDeleted);
    }

    @Test
    void supprimerPatient_recordNoLongerAccessibleAfterDeletion() {
        PatientResponseDTO saved = patientService.ajouterPatient(patientdto);
        patientService.supprimerPatient(saved.getId());

        assertThrows(Exception.class,
                () -> patientService.supprimerPatient(saved.getId()));
    }
}