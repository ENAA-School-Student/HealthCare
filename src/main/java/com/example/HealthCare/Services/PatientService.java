package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.CreatePatientDTO;
import com.example.HealthCare.DTO.PatientDTO;
import com.example.HealthCare.Mapper.PatientMapper;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientMapper patientMapper;




   public void ajouterPatient(CreatePatientDTO patient){
        patientRepository.save(patientMapper.toEntity(patient));
    }

    public List<PatientDTO> listerPatients(){

            List<Patient> patients = patientRepository.findAll();
            return patients
                    .stream()
                    .map((patient)->{
                        PatientDTO dto = patientMapper.toDto(patient);
                        dto.setTotalRendezVous(patient.getRenderVousList().size());
                        return dto;
                    })
                    .toList();
    }

    public Patient modifierPatient(int patientId, CreatePatientDTO newPatient){
       Patient patient = patientRepository.findById(patientId).orElseThrow(()->new RuntimeException("Patient Not Found with id " + patientId));
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


    public PatientDTO consulterPatient(int patientId){
       Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new RuntimeException("Patient Not Found !!"));
       return patientMapper.toDto(patient);
    }

    public void supprimerPatient(int patientId){
        patientRepository.deleteById(patientId);
    }


}
