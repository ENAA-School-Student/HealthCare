package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.DossierMedicalDTO;
import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import com.example.HealthCare.Exceptions.ResourceNotFoundException;
import com.example.HealthCare.Mapper.RendezVousMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import com.example.HealthCare.Repositories.MedecinRepository;
import com.example.HealthCare.Repositories.PatientRepository;
import com.example.HealthCare.Repositories.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<RendezVousResponseDTO> listerRendezVous(Pageable pageable){
        Page<RenderVous> renderVousList = rendezVousRepository.findAll(pageable);
        return renderVousList.map((dossierMedical)->{
          RendezVousResponseDTO rendezVous = rendezVousMapper.toDto(dossierMedical);
           return rendezVous;
               });
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

}
