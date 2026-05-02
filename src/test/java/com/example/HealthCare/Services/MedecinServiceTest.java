package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.MedecineRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.Models.Medecine;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MedecinServiceTest {


    @Autowired
    private MedecinService medecinService;

    MedecineRequestDTO medecineRequestDTO;
    @BeforeEach
    public void setUp(){
        medecineRequestDTO = new MedecineRequestDTO();
        medecineRequestDTO.setNom("Hadi");
        medecineRequestDTO.setEmail("souhaybhadi@gmail.com");
        medecineRequestDTO.setTelephone("0621421383");
    }

    @Test
    void ajouterMedecine() {
        MedecinResponseDTO save = medecinService.ajouterMedecine(medecineRequestDTO);
        assertNotNull(save.getNom());
        assertEquals("Hadi",save.getNom());
    }


    @Test
    void supprimerMedecine() {
        MedecinResponseDTO save = medecinService.ajouterMedecine(medecineRequestDTO);
        assertEquals("Hadi",save.getNom());
        Boolean isDeleted = medecinService.supprimerMedecine(save.getId());
        assertTrue(isDeleted);
    }


    @Test
    void modifierMedecine(){
        MedecinResponseDTO editedMedcin = medecinService.ajouterMedecine(medecineRequestDTO);
        MedecineRequestDTO medecinRequest = new MedecineRequestDTO("Souhayb","54545878","souhaybHadi@gmail.com","analyser");
        MedecinResponseDTO medecinResponseDTO = medecinService.modifierMedecine(editedMedcin.getId(),medecinRequest);
        assertEquals("Souhayb",medecinResponseDTO.getNom());
        assertEquals(editedMedcin.getId(), medecinResponseDTO.getId());
    }


}