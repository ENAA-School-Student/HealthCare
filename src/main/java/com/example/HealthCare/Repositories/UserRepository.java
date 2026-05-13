package com.example.HealthCare.Repositories;

import com.example.HealthCare.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity>  findByUsername(String username);
}
