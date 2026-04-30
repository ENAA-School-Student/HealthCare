package com.example.HealthCare.Services;

import com.example.HealthCare.Mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DossierMedicalServiceTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientMapper patientMapper;



    @BeforeEach
    public void setUp(){


    }
    @Test
    void ajouterDiagnostic() {

    }
}