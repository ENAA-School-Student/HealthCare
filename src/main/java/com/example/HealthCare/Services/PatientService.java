package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Exceptions.ResourceNotFoundException;
import com.example.HealthCare.Mapper.PatientMapper;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientMapper patientMapper;





   public PatientResponseDTO ajouterPatient(PatientRequestDTO patient){
     Patient patient1 =  patientMapper.toEntity(patient);
     return patientMapper.toDto(patientRepository.save(patient1));
    }

    public List<PatientResponseDTO> listerPatients(){
            List<Patient> patients = patientRepository.findAll();
            return patients
                    .stream()
                    .map((patient)->{
                        PatientResponseDTO dto = patientMapper.toDto(patient);
                        dto.setTotalRendezVous(patient.getRenderVousList().size());
                        return dto;
                    })
                    .toList();
    }

    public Patient modifierPatient(int patientId, PatientRequestDTO newPatient){
       Patient patient = patientRepository.findById(patientId).orElseThrow(()->new ResourceNotFoundException("Patient Not Found with id " + patientId));
       if(patient != null){
           patient.setNom(newPatient.getNom());
           patient.setPrenom(newPatient.getPrenom());
           patient.setEmail(newPatient.getEmail());
           patient.setTelephone(newPatient.getTelephone());
           patient.setDateNaissance(newPatient.getDateNaissance());
           return patientRepository.save(patient);
       }
       return null;
    }


    public PatientResponseDTO consulterPatient(int patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new ResourceNotFoundException("Patient Not Found with id " + patientId));
       return patientMapper.toDto(patient);
    }

    public Boolean supprimerPatient(int patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new ResourceNotFoundException("Patient Not Found with id " + patientId));
        if(patient != null){
            patientRepository.deleteById(patientId);
            return true;
        }
        return false;
    }




}
