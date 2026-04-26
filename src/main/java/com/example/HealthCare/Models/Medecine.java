package com.example.HealthCare.Models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medecine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String telephone;
    private String email;
    private String specialite;

    @OneToMany(mappedBy = "medecine")
    private List<RenderVous> renderVousList;
}
