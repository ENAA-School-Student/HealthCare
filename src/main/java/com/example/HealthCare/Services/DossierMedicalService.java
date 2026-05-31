package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.DossierMedicalRequestDTO;
import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.Exceptions.ResourceNotFoundException;
import com.example.HealthCare.Mapper.DossierMedicalMapper;
import com.example.HealthCare.Models.DossierMedical;
import com.example.HealthCare.Repositories.DossierMedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DossierMedicalService {


    @Autowired
    private  DossierMedicalRepository dossierMedicalRepository;
    @Autowired
    private  DossierMedicalMapper dossierMedicalMapper;


    public Page<DossierMedicalDTO> findAllDossierMedical(int page , int size){
        Pageable pageable = PageRequest.of(page, size);
      return  dossierMedicalRepository.findAll(pageable).map(
                (dossierMedical -> {
                    DossierMedicalDTO dossierMedicalDTO = dossierMedicalMapper.toDto(dossierMedical);
                    return  dossierMedicalDTO;
                }));
    }

    public void ajouterDossierMedicalPourPatient(DossierMedicalRequestDTO dossieMedicalDTO){
        dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossieMedicalDTO));
    }

    public DossierMedicalDTO ajouterDiagnostic(int dossierMedicalId , String diagnostic){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(dossierMedicalId).orElseThrow(()-> new ResourceNotFoundException("Dossier medical with ID " + dossierMedicalId + " Not Found !!"));
        dossierMedical.setDiagnostic(diagnostic);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    public DossierMedicalDTO ajouterObservations(int dossierMedicalId , String observations){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(dossierMedicalId).orElseThrow(()-> new ResourceNotFoundException("Dossier medical with ID " + dossierMedicalId + " Not Found !!"));
        dossierMedical.setObservations(observations);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    public DossierMedicalDTO consulterUnDossierMedical(int dossierMedicalId){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(dossierMedicalId).orElseThrow(()-> new ResourceNotFoundException("Dossier medical with ID " + dossierMedicalId + " Not Found !!"));
        return dossierMedicalMapper.toDto(dossierMedical);
    }

}
