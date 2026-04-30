package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.RendezVousRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RendezVousServiceTest {


    @Autowired
    private RendezVousService rendezVousService;
RendezVousRequestDTO rendezVousRequestDTO;
    @BeforeEach
    public void setUp(){
        rendezVousRequestDTO = new RendezVousRequestDTO();
        rendezVousRequestDTO.setStatut("availble");

    }
    @Test
    void creeRendezVous() {
    }

    @Test
    void listerRendezVous() {
    }

    @Test
    void annulerRendezVous() {
    }

    @Test
    void findRendezVousByMedecin() {
    }

    @Test
    void findRendezVousByPatient() {
    }

    @Test
    void modifierRendezVous() {
    }
}