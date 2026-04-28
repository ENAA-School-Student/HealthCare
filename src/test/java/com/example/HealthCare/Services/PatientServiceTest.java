package com.example.HealthCare.Services;

import com.example.HealthCare.Models.Patient;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.internal.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    void ajouterPatient() {
Patient patient = new Patient();
patient.setPrenom("souhayb");



    }

    @Test
    void supprimerPatient() {
    }
}