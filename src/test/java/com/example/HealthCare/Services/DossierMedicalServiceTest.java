package com.example.HealthCare.Services;

import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.Mapper.DossierMedicalMapper;
import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Repositories.DossierMedicalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DossierMedicalServiceTest {

    @Autowired
    private DossierMedicalService dossierMedicalService;
    @Autowired
    private DossierMedicalMapper dossierMedicalMapper;


    DossierMedicalDTO dossierMedicalDTO;
    @BeforeEach
    public void setUp(){
        dossierMedicalDTO = new DossierMedicalDTO();
        dossierMedicalDTO.setId(1);
        dossierMedicalDTO.setDiagnostic("malariya");
        dossierMedicalDTO.setObservations("keep your treatment");
    }
    @Test
    void ajouterDiagnostic() {
       //assertEquals("malariya",dossierMedicalDTO.getDiagnostic());
        DossierMedicalDTO dto = dossierMedicalService.ajouterDiagnostic(dossierMedicalDTO.getId() , "Cancer");
        assertEquals("Cancer",dto.getDiagnostic());
    }
}