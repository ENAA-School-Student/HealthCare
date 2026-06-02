package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.PatientRequestDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Exceptions.ResourceNotFoundException;
import com.example.HealthCare.Mapper.PatientMapper;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.UserEntity;
import com.example.HealthCare.Repositories.PatientRepository;
import com.example.HealthCare.Repositories.UserRepository;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



   public Page<PatientResponseDTO> findPatientByDateLissance(LocalDate date, int page , int size){
       Pageable pageable =  PageRequest.of(page, size);
       return  patientRepository.findPatientsByDateNaissance(date , pageable).map(patient ->
       {
           PatientResponseDTO patientResponseDTO = patientMapper.toDto(patient);
           return  patientResponseDTO;
       });
   }



    public Page<PatientResponseDTO> findAll(int pageNumber , int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return patientRepository.findAll(pageable).map((patient ->
        {
            PatientResponseDTO patientResponseDTO = patientMapper.toDto(patient);
            return  patientResponseDTO;
        }));
    }

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


    public PatientResponseDTO getMonProfil() {
        UserEntity currentUser = getCurrentUser();
        Patient patient = patientRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Profil patient non trouvé pour l'utilisateur connecté"));
        return patientMapper.toDto(patient);
    }


    public PatientResponseDTO modifierMonProfil(PatientRequestDTO patientRequestDTO) {
        UserEntity currentUser = getCurrentUser();
        Patient patient = patientRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Profil patient non trouvé pour l'utilisateur connecté"));

        patient.setNom(patientRequestDTO.getNom());
        patient.setPrenom(patientRequestDTO.getPrenom());
        patient.setTelephone(patientRequestDTO.getTelephone());
        patient.setDateNaissance(patientRequestDTO.getDateNaissance());
        return patientMapper.toDto(patientRepository.save(patient));
    }


    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + username));
    }
}
