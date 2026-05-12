package com.example.HealthCare.Repositories;

import com.example.HealthCare.DTO.UserInfo;
import com.example.HealthCare.Models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    UserInfo findByUsername(String username);
}
