package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.MedecineRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.Mapper.MedecinMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Repositories.MedecinRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {


    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private MedecinMapper medecinMapper;




    public MedecinResponseDTO ajouterMedecine(MedecineRequestDTO medecinDTO){
        Medecine medecine =  medecinMapper.toEntity(medecinDTO);
       return medecinMapper.toDto(medecinRepository.save(medecine));
    }

    public Boolean supprimerMedecine(int medecinId){
        Medecine medecine = medecinRepository.findById(medecinId).orElseThrow(()->new RuntimeException("not found with id " + medecinId));
        if(medecine != null){
            medecinRepository.deleteById(medecinId);
            return true;
        }
        return false;
    }

    public List<MedecinResponseDTO> listerMedecine(){
        List<Medecine> medecineList = medecinRepository.findAll();
      return medecineList
                .stream()
                .map((medecine)->{
                    MedecinResponseDTO medecinResponseDTO = medecinMapper.toDto(medecine);
                    medecinResponseDTO.setTotalRendezVous(medecine.getRenderVousList().size());
                    return medecinResponseDTO;
                })
              .toList();
    }

    public MedecinResponseDTO modifierMedecine(int medecinId , MedecineRequestDTO medecinDTO){
        Medecine medecine = medecinRepository.findById(medecinId).orElseThrow(()->new RuntimeException("not found with id " + medecinId));
        if(medecine != null){
            medecine.setNom(medecinDTO.getNom());
            medecine.setEmail(medecinDTO.getEmail());
            medecine.setTelephone(medecinDTO.getTelephone());
            medecine.setSpecialite(medecinDTO.getSpecialite());
            return medecinMapper.toDto(medecinRepository.save(medecine));
        }
        return null;
    }



}
