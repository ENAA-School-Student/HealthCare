package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PatientServiceTest {

    @Autowired
    private PatientService patientService;



   private PatientRequestDTO patientdto;
   @BeforeEach
   public void setUp(){
       patientdto = new PatientRequestDTO();
       patientdto.setNom("Hadi");
       patientdto.setPrenom("souhayb");
       patientdto.setEmail("souhaybhadi@gmail.com");
       patientdto.setTelephone("0621421383");
    }

    @Test
    void ajouterPatient() {
        PatientResponseDTO save = patientService.ajouterPatient(patientdto);
        assertNotNull(save.getNom());
        assertEquals("Hadi",save.getNom());
    }


    @Test
    void supprimerPatient() {
        PatientResponseDTO save = patientService.ajouterPatient(patientdto);
        Boolean isDeleted = patientService.supprimerPatient(save.getId());
        assertEquals("Hadi",save.getNom());
        assertTrue(isDeleted);
    }


}