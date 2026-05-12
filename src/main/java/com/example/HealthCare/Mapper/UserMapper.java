package com.example.HealthCare.Mapper;

import com.example.HealthCare.DTO.UserAuthRequest;
import com.example.HealthCare.DTO.UserInfo;
import com.example.HealthCare.Models.UserDetails;
import org.apache.catalina.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDetails toEntity(UserAuthRequest user);
    UserInfo toDto(UserDetails user);
}
