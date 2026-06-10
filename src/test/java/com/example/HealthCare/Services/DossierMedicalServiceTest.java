package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.Mapper.DossierMedicalMapper;
import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Repositories.DossierMedicalRepository;
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
class DossierMedicalServiceTest {

    @Autowired
    private DossierMedicalService dossierMedicalService;

    @Autowired
    private DossierMedicalRepository dossierMedicalRepository;

    @Autowired
    private DossierMedicalMapper dossierMedicalMapper;

    private long savedId;

    @BeforeEach
    void setUp() {

        DossierMedical dossierMedical = new DossierMedical();
        dossierMedical.setDiagnostic("malariya");
        dossierMedical.setObservations("keep your treatment");
        savedId = dossierMedicalRepository.save(dossierMedical).getId();
    }


    @Test
    void ajouterDiagnostic_updatesDiagnostic() {
        DossierMedicalDTO dto = dossierMedicalService.ajouterDiagnostic((int) savedId, "Cancer");

        assertEquals("Cancer", dto.getDiagnostic());
    }

    @Test
    void ajouterDiagnostic_preservesObservations() {
        DossierMedicalDTO dto = dossierMedicalService.ajouterDiagnostic((int) savedId, "Cancer");

        assertEquals("keep your treatment", dto.getObservations());
    }


    @Test
    void ajouterObservations_updatesObservations() {
        DossierMedicalDTO dto = dossierMedicalService.ajouterObservations((int) savedId, "New observation");

        assertEquals("New observation", dto.getObservations());
    }


    @Test
    void consulterUnDossierMedical_returnsCorrectDossier() {
        DossierMedicalDTO dto = dossierMedicalService.consulterUnDossierMedical((int) savedId);

        assertNotNull(dto);
        assertEquals("malariya", dto.getDiagnostic());
    }


    @Test
    void findAllDossierMedical_containsInsertedRecord() {
        var page = dossierMedicalService.findAllDossierMedical(0, 10);

        assertFalse(page.getContent().isEmpty());
        assertTrue(page.getContent().stream().anyMatch(d -> d.getId() == savedId));
    }
}