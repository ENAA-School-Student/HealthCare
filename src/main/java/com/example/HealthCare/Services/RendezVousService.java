package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import com.example.HealthCare.Exceptions.ResourceNotFoundException;
import com.example.HealthCare.Mapper.RendezVousMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import com.example.HealthCare.Models.UserEntity;
import com.example.HealthCare.Repositories.MedecinRepository;
import com.example.HealthCare.Repositories.PatientRepository;
import com.example.HealthCare.Repositories.RendezVousRepository;
import com.example.HealthCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousMapper rendezVousMapper;
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private UserRepository userRepository;



    public RendezVousResponseDTO creeRendezVous(RendezVousRequestDTO rendezVousDTO){
        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Paitent with ID " + rendezVousDTO.getPatientId() + " Not Found !!"));
        Medecine medecine = medecinRepository.findById(rendezVousDTO.getMedecinId()).orElseThrow(() -> new ResourceNotFoundException("Medecin with ID " + rendezVousDTO.getMedecinId() + " Not Found !!"));
        RenderVous renderVous = rendezVousMapper.toEntity(rendezVousDTO);
        if(patient != null && medecine != null){
            renderVous.setPatient(patient);
            renderVous.setMedecine(medecine);
        }
        return rendezVousMapper.toDto(rendezVousRepository.save(renderVous));
    }





    @Cacheable(value = "livraisonCache", key = "#pageNumber + '-' + #size")
    public Page<RendezVousResponseDTO> findAll(int pageNumber , int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return rendezVousRepository.findAll(pageable).map((rendezVous ->
        {
            RendezVousResponseDTO rendezVousResponseDTO = rendezVousMapper.toDto(rendezVous);
            return  rendezVousResponseDTO;
        }));
    }

    public RendezVousResponseDTO annulerRendezVous(int rendezVousId){
        RenderVous renderVous = rendezVousRepository.findById(rendezVousId).orElseThrow(()->new ResourceNotFoundException("Rendez Vous with ID " + rendezVousId + " Not Found !!"));
        renderVous.setStatut(RendezVousStatutEnum.ANNULE);
        return  rendezVousMapper.toDto(rendezVousRepository.save(renderVous));
    }


    public List<RendezVousResponseDTO> findRendezVousByMedecin(int medecineId){
        return rendezVousMapper.toDtoList(rendezVousRepository.findByMedecine_Id(medecineId)) ;
    }

    public List<RendezVousResponseDTO> findRendezVousByPatient(int patientId){
        return rendezVousMapper.toDtoList(rendezVousRepository.findByPatient_Id(patientId)) ;
    }

    public RendezVousResponseDTO modifierRendezVous(int rendezVousId , RendezVousRequestDTO newRendezVous){
        RenderVous renderVous = rendezVousRepository.findById(rendezVousId).orElseThrow(()->new ResourceNotFoundException("Rendez Vous with ID " + rendezVousId + " Not Found !!"));
        if(renderVous != null){
            renderVous.getPatient().setId(newRendezVous.getPatientId());
            renderVous.getMedecine().setId(newRendezVous.getMedecinId());
            renderVous.setDateRendezVous(newRendezVous.getDateRendezVous());
            renderVous.setStatut(newRendezVous.getStatut());
            return rendezVousMapper.toDto(rendezVousRepository.save(renderVous));
        }
        return  null;
    }


    public Page<RendezVousResponseDTO> findRendezVousByStatutRendezVous(RendezVousStatutEnum statut , int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateRendezVous").ascending());
        return  rendezVousRepository.findRenderVousByStatut(statut, pageable).map(
                (renderVous -> {RendezVousResponseDTO rendezVousResponseDTO = rendezVousMapper.toDto(renderVous);
                    return rendezVousResponseDTO;})
        );
    }



    public List<RendezVousResponseDTO> getMesRendezVous() {
        UserEntity currentUser = getCurrentUser();
        Patient patient = patientRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Profil patient non trouvé pour l'utilisateur connecté"));
        return findRendezVousByPatient(patient.getId());
    }


    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + username));
    }
}