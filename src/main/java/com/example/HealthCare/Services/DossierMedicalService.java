package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.CreateDossieMedicalDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.Mapper.DossierMedicalMapper;
import com.example.HealthCare.Repositories.DossierMedicalRepository;
import org.springframework.stereotype.Service;

@Service
public class DossierMedicalService {


    private final DossierMedicalRepository dossierMedicalRepository;
    private final DossierMedicalMapper dossierMedicalMapper;


    public DossierMedicalService(DossierMedicalRepository dossierMedicalRepository, DossierMedicalMapper dossierMedicalMapper) {
        this.dossierMedicalRepository = dossierMedicalRepository;
        this.dossierMedicalMapper = dossierMedicalMapper;
    }


    public void ajouterDossierMedicalPourPatient(CreateDossieMedicalDTO dossieMedicalDTO){
        dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossieMedicalDTO));
    }



}
