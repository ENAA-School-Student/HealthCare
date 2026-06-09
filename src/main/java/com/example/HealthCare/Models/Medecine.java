package com.example.HealthCare.Models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medecine extends UserEntity{

    private String nom;
    private String telephone;
    private String specialite;

    @OneToMany(mappedBy = "medecine")
    private List<RenderVous> renderVousList;
}
