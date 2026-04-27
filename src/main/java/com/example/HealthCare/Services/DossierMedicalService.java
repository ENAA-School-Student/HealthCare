package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.CreateDossieMedicalDTO;
import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.Mapper.DossierMedicalMapper;
import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Repositories.DossierMedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DossierMedicalService {


    @Autowired
    private  DossierMedicalRepository dossierMedicalRepository;
    @Autowired
    private  DossierMedicalMapper dossierMedicalMapper;


    public void ajouterDossierMedicalPourPatient(CreateDossieMedicalDTO dossieMedicalDTO){
        dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossieMedicalDTO));
    }

    public DossierMedicalDTO ajouterDiagnostic(int dossierMedicalId , String diagnostic){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(dossierMedicalId).orElseThrow(()-> new RuntimeException("Dossier Medical not found !!"));
        dossierMedical.setDiagnostic(diagnostic);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }


    public DossierMedicalDTO ajouterObservations(int dossierMedicalId , String observations){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(dossierMedicalId).orElseThrow(()-> new RuntimeException("Dossier Medical not found !!"));
        dossierMedical.setObservations(observations);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    public DossierMedicalDTO consulterUnDossierMedical(int dossierMedicalId){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(dossierMedicalId).orElseThrow(()-> new RuntimeException("Dossier Medical not found !!"));
        return dossierMedicalMapper.toDto(dossierMedical);
    }

}
