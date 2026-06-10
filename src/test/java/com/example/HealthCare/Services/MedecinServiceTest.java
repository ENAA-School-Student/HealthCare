package com.example.HealthCare.Services;

import static org.junit.jupiter.api.Assertions.*;


import com.example.HealthCare.DTO.MedecineRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MedecinServiceTest {

    @Autowired
    private MedecinService medecinService;

    private MedecineRequestDTO medecineRequestDTO;

    @BeforeEach
    void setUp() {
        medecineRequestDTO = new MedecineRequestDTO();
        medecineRequestDTO.setNom("Hadi");
        medecineRequestDTO.setEmail("souhaybhadi@gmail.com");
        medecineRequestDTO.setTelephone("0621421383");
    }



    @Test
    void ajouterMedecine_savesAndReturnsDTO() {
        MedecinResponseDTO saved = medecinService.ajouterMedecine(medecineRequestDTO);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Hadi", saved.getNom());
        assertEquals("souhaybhadi@gmail.com", saved.getEmail());
        assertEquals("0621421383", saved.getTelephone());
    }



    @Test
    void supprimerMedecine_returnsTrueWhenDeleted() {
        MedecinResponseDTO saved = medecinService.ajouterMedecine(medecineRequestDTO);

        Boolean isDeleted = medecinService.supprimerMedecine(saved.getId());

        assertTrue(isDeleted);
    }

    @Test
    void supprimerMedecine_recordNoLongerAccessibleAfterDeletion() {
        MedecinResponseDTO saved = medecinService.ajouterMedecine(medecineRequestDTO);
        medecinService.supprimerMedecine(saved.getId());

        assertThrows(Exception.class,
                () -> medecinService.supprimerMedecine(saved.getId()));
    }

    

    @Test
    void modifierMedecine_updatesFieldsCorrectly() {
        MedecinResponseDTO saved = medecinService.ajouterMedecine(medecineRequestDTO);

        MedecineRequestDTO updateRequest =
                new MedecineRequestDTO("Souhayb", "54545878", "souhaybHadi@gmail.com", "analyser");

        MedecinResponseDTO updated = medecinService.modifierMedecine(saved.getId(), updateRequest);

        assertEquals("Souhayb", updated.getNom());
        assertEquals("souhaybHadi@gmail.com", updated.getEmail());
        assertEquals("54545878", updated.getTelephone());
    }

    @Test
    void modifierMedecine_preservesId() {
        MedecinResponseDTO saved = medecinService.ajouterMedecine(medecineRequestDTO);

        MedecineRequestDTO updateRequest =
                new MedecineRequestDTO("Souhayb", "54545878", "souhaybHadi@gmail.com", "analyser");

        MedecinResponseDTO updated = medecinService.modifierMedecine(saved.getId(), updateRequest);

        assertEquals(saved.getId(), updated.getId());
    }
}