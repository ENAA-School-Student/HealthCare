package com.example.HealthCare.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN,
    MEDECIN,
    PATIENT;


    @JsonValue
    public String toJson() {
        return name();
    }

    @JsonCreator
    public static Role fromJson(String value) {
        return Role.valueOf(value.toUpperCase()); // accepts "admin", "ADMIN"
    }
}
