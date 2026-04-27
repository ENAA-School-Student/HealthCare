package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.CreateMedecinDTO;
import com.example.HealthCare.DTO.MedecinDTO;
import com.example.HealthCare.Mapper.MedecinMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Repositories.MedecinRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {


    private MedecinRepository medecinRepository;
    private MedecinMapper medecinMapper;

    public MedecinService(MedecinRepository medecinRepository, MedecinMapper medecinMapper) {
        this.medecinRepository = medecinRepository;
        this.medecinMapper = medecinMapper;
    }


    public void ajouterMedecine(CreateMedecinDTO medecinDTO){
        medecinRepository.save(medecinMapper.toEntity(medecinDTO));
    }

    public void supprimerMedecine(int medecinId){
        medecinRepository.deleteById(medecinId);
    }

    public List<MedecinDTO> listerMedecine(){
        List<Medecine> medecineList = medecinRepository.findAll();
      return medecineList
                .stream()
                .map((medecine)->{
                    MedecinDTO medecinDTO = medecinMapper.toDto(medecine);
                    medecinDTO.setTotalRendezVous(medecine.getRenderVousList().size());
                    return medecinDTO;
                })
              .toList();
    }

    public Medecine modifierMedecine(int medecinId , CreateMedecinDTO medecinDTO){
        Medecine medecine = medecinRepository.findById(medecinId).orElseThrow(()->new RuntimeException("not found with id " + medecinId));
        if(medecine != null){
            medecine.setNom(medecinDTO.getNom());
            medecine.setEmail(medecinDTO.getEmail());
            medecine.setTelephone(medecinDTO.getTelephone());
            medecine.setSpecialite(medecinDTO.getSpecialite());
            return medecinRepository.save(medecine);
        }
        return null;
    }



}
