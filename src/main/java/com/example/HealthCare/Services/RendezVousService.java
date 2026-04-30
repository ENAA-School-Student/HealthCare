package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Mapper.RendezVousMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import com.example.HealthCare.Repositories.MedecinRepository;
import com.example.HealthCare.Repositories.PatientRepository;
import com.example.HealthCare.Repositories.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
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



    public void creeRendezVous(RendezVousRequestDTO rendezVousDTO){
        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId()).orElseThrow(() -> new RuntimeException("Paitent Not Found!!"));
        Medecine medecine = medecinRepository.findById(rendezVousDTO.getMedecinId()).orElseThrow(() -> new RuntimeException("Medecin Not found !!"));
        RenderVous rendezVous = rendezVousMapper.toEntity(rendezVousDTO);
        if(patient != null && medecine != null){
            rendezVous.setPatient(patient);
            rendezVous.setMedecine(medecine);
        }
        rendezVousRepository.save(rendezVous);
    }


    public List<RendezVousResponseDTO> listerRendezVous(){
        List<RenderVous> renderVousList = rendezVousRepository.findAll();
        List<RendezVousResponseDTO> renderVousDto = new ArrayList<>();
        for(RenderVous renderVous : renderVousList){
            RendezVousResponseDTO dto = rendezVousMapper.toDto(renderVous);
            renderVousDto.add(dto);
        }
      return renderVousDto;
    }


    public RenderVous annulerRendezVous(int rendezVousId){
          RenderVous renderVous= rendezVousRepository.findById(rendezVousId).orElseThrow(()->new RuntimeException("not found"));
          renderVous.setStatut("le RendezVous avec ID " + rendezVousId + " etait Anuller");
          return rendezVousRepository.save(renderVous);
    }


    public RendezVousResponseDTO findRendezVousByMedecin(int medecineId){
      return rendezVousMapper.toDto(rendezVousRepository.findByMedecine_Id(medecineId)) ;
    }
    public RendezVousResponseDTO findRendezVousByPatient(int patientId){
        return rendezVousMapper.toDto(rendezVousRepository.findByPatient_Id(patientId)) ;
    }


    public void modifierRendezVous(int rendezVousId , RendezVousRequestDTO newRendezVous){
        RenderVous renderVous = rendezVousRepository.findById(rendezVousId).orElseThrow(()->new RuntimeException("Rendez vous not found !!"));
if(renderVous != null){
    renderVous.getPatient().setId(newRendezVous.getPatientId());
    renderVous.getMedecine().setId(newRendezVous.getMedecinId());
    renderVous.setDateRendezVous(newRendezVous.getDateRendezVous());
    renderVous.setStatut(newRendezVous.getStatut());
    rendezVousRepository.save(renderVous);
}
    }
}
