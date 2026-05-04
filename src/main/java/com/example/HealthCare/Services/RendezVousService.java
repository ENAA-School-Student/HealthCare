package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.DTO.RendezVousRequestDTO;
import com.example.HealthCare.DTO.RendezVousResponseDTO;
import com.example.HealthCare.Enums.RendezVousStatutEnum;
import com.example.HealthCare.Interfaces.MedecinRendezVousCount;
import com.example.HealthCare.Mapper.PatientMapper;
import com.example.HealthCare.Mapper.RendezVousMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.RenderVous;
import com.example.HealthCare.Repositories.MedecinRepository;
import com.example.HealthCare.Repositories.PatientRepository;
import com.example.HealthCare.Repositories.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    private PatientMapper patientMapper;



    public RendezVousResponseDTO creeRendezVous(RendezVousRequestDTO rendezVousDTO){
        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId()).orElseThrow(() -> new RuntimeException("Paitent Not Found!!"));
        Medecine medecine = medecinRepository.findById(rendezVousDTO.getMedecinId()).orElseThrow(() -> new RuntimeException("Medecin Not found !!"));
        RenderVous renderVous = rendezVousMapper.toEntity(rendezVousDTO);
        if(patient != null && medecine != null){
            renderVous.setPatient(patient);
            renderVous.setMedecine(medecine);
        }
       return rendezVousMapper.toDto(rendezVousRepository.save(renderVous));
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


    public RendezVousResponseDTO annulerRendezVous(int rendezVousId){
          RenderVous renderVous = rendezVousRepository.findById(rendezVousId).orElseThrow(()->new RuntimeException("not found"));
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
        RenderVous renderVous = rendezVousRepository.findById(rendezVousId).orElseThrow(()->new RuntimeException("Rendez vous not found !!"));
if(renderVous != null){
    renderVous.getPatient().setId(newRendezVous.getPatientId());
    renderVous.getMedecine().setId(newRendezVous.getMedecinId());
    renderVous.setDateRendezVous(newRendezVous.getDateRendezVous());
    renderVous.setStatut(newRendezVous.getStatut());
   return rendezVousMapper.toDto(rendezVousRepository.save(renderVous));
}
return  null;
    }




    public List<RendezVousResponseDTO> findRendezVousByGivinDateAndMedecinId(LocalDate date , int medcinId){
      return rendezVousMapper.toDtoList(rendezVousRepository.rendezVousByMedecinAndDate(date,medcinId));
    }

    public List<RendezVousResponseDTO> findRendezVousByGivinDateAndPatientId(LocalDate date , int patientId){
        return rendezVousMapper.toDtoList(rendezVousRepository.rendezVousByPatientAndDate(patientId,date));
    }


    public List<RendezVousResponseDTO> findrenderVousParPatient(int id){
       List<RenderVous> rs = rendezVousRepository.renderVousParPatient(id);
      return rs.stream()
               .map((renderVous) ->
                       {RendezVousResponseDTO dto = rendezVousMapper.toDto(renderVous);
                       return dto;
                       }).toList();

    }

    public List<MedecinRendezVousCount> getMedecenTotalRendezVous(){
        return rendezVousRepository.medecinRendezVousCount();
    }

    public List<PatientResponseDTO> getPatientTotalRendezVousGretaerthan(int number){
        List<Patient> rs = rendezVousRepository.patientRendezVous(number);
        return rs.stream()
                .map((patient) ->
                        {
                            PatientResponseDTO dto = patientMapper.toDto(patient);
                            return dto;
                        }
                        ).toList();
    }
}
