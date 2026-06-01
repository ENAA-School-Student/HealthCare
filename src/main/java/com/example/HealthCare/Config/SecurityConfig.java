package com.example.HealthCare.Config;

import com.example.HealthCare.Enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // ===== PATIENT ENDPOINTS =====
                        // Patients can view and modify their own profile
                        .requestMatchers(HttpMethod.GET,"/api/patients/mon-profil").hasRole(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.PUT,"/api/patients/modifier-profil").hasRole(Role.PATIENT.name())
                        // Patients cannot list all patients or view other patients
                        .requestMatchers(HttpMethod.GET,"/api/patients/listerLesPatients").hasRole(Role.ADMIN.name())

                        // ===== RENDEZVOUS ENDPOINTS =====
                        // Patients can view their own appointments
                        .requestMatchers(HttpMethod.GET,"/api/rendezVous/mes-rendez-vous").hasRole(Role.PATIENT.name())

                        // ===== DOSSIER MEDICAL ENDPOINTS =====
                        // Patients can view their own medical file
                        .requestMatchers(HttpMethod.GET,"/api/dossierMedical/mon-dossier").hasRole(Role.PATIENT.name())
                        // Patients CANNOT modify medical files (only doctors can)
                        .requestMatchers(HttpMethod.POST,"/api/dossierMedical/ajouterDiagnostic/{id}").hasRole(Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/dossierMedical/ajouterObservations/{id}").hasRole(Role.MEDECIN.name())

                        .requestMatchers(HttpMethod.POST,"/api/patients/AjouterUnPatient").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/patients/listerLesPatientsPagination").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/patients/consulterPatientPar{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/patients/modifierUnPatient/{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/patients/supprimerUnPatient/{id}").hasRole(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST,"/api/medecins/AjouterMedecine").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/medecins/ListerMedecines").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/medecins/modifierMedecine/{id}").hasAnyRole(Role.ADMIN.name(),Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/medecins/supprimerMedecine/{id}").hasRole(Role.ADMIN.name())


                        .requestMatchers(HttpMethod.POST,"/api/dossierMedical/creeUnDossierMedical").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/dossierMedical/ajouterDiagnostic/{id}").hasRole(Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/dossierMedical/ajouterObservations/{id}").hasRole(Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/dossierMedical/consulterDossierMedical/{id}").hasRole(Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/dossierMedical/getAllDossierMedical").hasRole(Role.ADMIN.name())


                        .requestMatchers(HttpMethod.POST,"/api/rendezVous/creeUnRendezVous").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/rendezVous/listerLesRendezVous").hasAnyRole(Role.ADMIN.name(),Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/rendezVous/listerLesRendezVousPagination").hasAnyRole(Role.ADMIN.name(),Role.MEDECIN.name())
                        .requestMatchers(HttpMethod.PATCH,"/api/rendezVous/annulerRendezVous/{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/rendezVous/findRendezVousByMedecine{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/rendezVous/findRendezVousByPatient{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/rendezVous/modifierRendezVousById/{id}").hasRole(Role.ADMIN.name())

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}