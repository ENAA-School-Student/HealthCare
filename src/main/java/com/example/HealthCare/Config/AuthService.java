package com.example.HealthCare.Config;

import com.example.HealthCare.DTO.AuthResponse;
import com.example.HealthCare.DTO.UserAuthRequest;
import com.example.HealthCare.Enums.Role;
import com.example.HealthCare.Models.Admin;
import com.example.HealthCare.Models.Medecine;
import com.example.HealthCare.Models.Patient;
import com.example.HealthCare.Models.UserEntity;
import com.example.HealthCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserService userDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public void register(UserAuthRequest request) {
        Patient patient = new Patient();
        Medecine medecine = new Medecine();
        Admin admin = new Admin();
        switch (request.getRole().name()) {
            case "PATIENT" -> {
                patient.setUsername(request.getUserName());
                patient.setEmail(request.getEmail());
                patient.setRole(request.getRole());
                patient.setPassword(passwordEncoder.encode(request.getPassword()));
                patient.setPrenom(request.getPrenom());
                patient.setNom(request.getNom());
                patient.setTelephone(request.getTelephone());
                patient.setDateNaissance(request.getDateNaissance());
                userRepository.save(patient);
            }
            case "ADMIN" -> {
                admin.setUsername(request.getUserName());
                admin.setEmail(request.getEmail());
                admin.setPassword(passwordEncoder.encode(request.getPassword()));
                admin.setRole(request.getRole());
                userRepository.save(admin);
            }
            case "MEDECIN" -> {
                medecine.setUsername(request.getUserName());
                medecine.setEmail(request.getEmail());
                medecine.setPassword(passwordEncoder.encode(request.getPassword()));
                medecine.setRole(request.getRole());
                medecine.setNom(request.getNom());
                medecine.setSpecialite(request.getSpecialite());
                medecine.setTelephone(request.getTelephone());
                userRepository.save(medecine);
            }
        }


    }

    public AuthResponse login(UserAuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }


}
