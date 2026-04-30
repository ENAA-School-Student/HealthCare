package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.MedecinRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedecinServiceTest {


    @Autowired
    private MedecinService medecinService;

    MedecinRequestDTO medecinRequestDTO;
    @BeforeEach
    public void setUp(){
        medecinRequestDTO = new MedecinRequestDTO();
        medecinRequestDTO.setNom("Hadi");
        medecinRequestDTO.setEmail("souhaybhadi@gmail.com");
        medecinRequestDTO.setTelephone("0621421383");
    }

    @Test
    void ajouterMedecine() {
        MedecinResponseDTO save = medecinService.ajouterMedecine(medecinRequestDTO);
        assertNotNull(save.getNom());
        assertEquals("Hadi",save.getNom());
    }


    @Test
    void supprimerMedecine() {
        MedecinResponseDTO save = medecinService.ajouterMedecine(medecinRequestDTO);
        assertEquals("Hadi",save.getNom());
        Boolean isDeleted = medecinService.supprimerMedecine(save.getId());
        assertTrue(isDeleted);
    }


}