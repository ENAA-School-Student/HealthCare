package com.example.HealthCare.Services;

import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PdfGeneratorServiceTest {

    @InjectMocks
    private PdfGeneratorService pdfGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateDossierMedicalPdf() {
        Patient patient = new Patient();
        patient.setNom("Doe");
        patient.setPrenom("John");
        patient.setDateNaissance(LocalDate.of(1990, 1, 1));
        patient.setTelephone("123456789");

        DossierMedical dossier = new DossierMedical();
        dossier.setPatient(patient);
        dossier.setDiagnostic("Test Diagnostic");
        dossier.setObservations("Test Observations");
        dossier.setDateCreation(LocalDate.now());

        ByteArrayInputStream bis = pdfGeneratorService.generateDossierMedicalPdf(dossier);
        assertNotNull(bis);
    }

    @Test
    void testGeneratePatientAppointmentsPdf() {
        Patient patient = new Patient();
        patient.setNom("Doe");
        patient.setPrenom("John");

        Medecine medecin = new Medecine();
        medecin.setNom("Dr. Smith");

        RenderVous appointment = new RenderVous();
        appointment.setDateRendezVous(LocalDate.now());
        appointment.setStatut(RendezVousStatutEnum.CONFIRME);
        appointment.setMedecine(medecin);
        appointment.setPatient(patient);

        List<RenderVous> appointments = new ArrayList<>();
        appointments.add(appointment);

        ByteArrayInputStream bis = pdfGeneratorService.generatePatientAppointmentsPdf(patient, appointments);
        assertNotNull(bis);
    }

    @Test
    void testGenerateSimpleReportPdf() {
        ByteArrayInputStream bis = pdfGeneratorService.generateSimpleReportPdf("Test Title", "Test Content");
        assertNotNull(bis);
    }
}
