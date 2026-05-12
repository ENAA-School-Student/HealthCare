package com.example.HealthCare.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthRequest {
    @NotNull(message = "dateRendezVous is mandatory")
    private String userName;
    @NotNull(message = "dateRendezVous is mandatory")
    @Email
    private String email;
    @NotNull(message = "dateRendezVous is mandatory")
    private String password;
}
