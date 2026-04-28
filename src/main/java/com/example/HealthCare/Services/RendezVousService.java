package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.CreateRendezVousDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.DTO.PatientDTO;
import com.example.HealthCare.DTO.RendezVousDTO;
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



    public void creeRendezVous(CreateRendezVousDTO rendezVousDTO){

        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId()).orElseThrow(() -> new RuntimeException("Paitent Not Found!!"));
        Medecine medecine = medecinRepository.findById(rendezVousDTO.getMedecinId()).orElseThrow(() -> new RuntimeException("Medecin Not found !!"));
        RenderVous rendezVous = rendezVousMapper.toEntity(rendezVousDTO);
        if(patient != null && medecine != null){
            rendezVous.setPatient(patient);
            rendezVous.setMedecine(medecine);
        }
        rendezVousRepository.save(rendezVous);
    }


    public List<RendezVousDTO> listerRendezVous(){
        List<RenderVous> renderVousList = rendezVousRepository.findAll();
        List<RendezVousDTO> renderVousDto = new ArrayList<>();
        for(RenderVous renderVous : renderVousList){
            RendezVousDTO dto = rendezVousMapper.toDto(renderVous);
            renderVousDto.add(dto);
        }
      return renderVousDto;
    }


    public RenderVous annulerRendezVous(int rendezVousId){
          RenderVous renderVous= rendezVousRepository.findById(rendezVousId).orElseThrow(()->new RuntimeException("not found"));
          renderVous.setStatut("le RendezVous avec ID " + rendezVousId + " etait Anuller");
          return rendezVousRepository.save(renderVous);
    }


    public RendezVousDTO findRendezVousByMedecin(int medecineId){
      return rendezVousMapper.toDto(rendezVousRepository.findByMedecine_Id(medecineId)) ;
    }
    public RendezVousDTO findRendezVousByPatient(int patientId){
        return rendezVousMapper.toDto(rendezVousRepository.findByPatient_Id(patientId)) ;
    }


    public void modifierRendezVous(int rendezVousId , CreateRendezVousDTO newRendezVous){
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
