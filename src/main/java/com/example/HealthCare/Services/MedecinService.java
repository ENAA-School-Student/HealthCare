package com.example.HealthCare.Services;


import com.example.HealthCare.DTO.MedecineRequestDTO;
import com.example.HealthCare.DTO.MedecinResponseDTO;
import com.example.HealthCare.DTO.PatientResponseDTO;
import com.example.HealthCare.Exceptions.ResourceNotFoundException;
import com.example.HealthCare.Mapper.MedecinMapper;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Repositories.MedecinRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {


    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private MedecinMapper medecinMapper;


    @Cacheable(value = "MedecinsCache", key = "#pageNumber + '-' + #size")
    public Page<MedecinResponseDTO> findAllMedecinPagination(int pageNumber , int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return medecinRepository.findAll(pageable).map((medcin ->
        {
            MedecinResponseDTO medecinResponseDTO = medecinMapper.toDto(medcin);
            return  medecinResponseDTO;
        }));
    }

    public MedecinResponseDTO ajouterMedecine(MedecineRequestDTO medecinDTO){
        Medecine medecine =  medecinMapper.toEntity(medecinDTO);
        return medecinMapper.toDto(medecinRepository.save(medecine));
    }

    public Boolean supprimerMedecine(long medecinId){
        Medecine medecine = medecinRepository.findById(medecinId).orElseThrow(() -> new ResourceNotFoundException("Medecin with ID " + medecinId + " Not Found !!"));
        if(medecine != null){
            medecinRepository.deleteById(medecinId);
            return true;
        }
        return false;
    }



    public MedecinResponseDTO modifierMedecine(long medecinId , MedecineRequestDTO medecinDTO){
        Medecine medecine = medecinRepository.findById(medecinId).orElseThrow(() -> new ResourceNotFoundException("Medecin with ID " + medecinId + " Not Found !!"));
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
