package com.example.warehousemanagement.security.domain.dto;

import com.example.warehousemanagement.user.domain.Role;
import com.example.warehousemanagement.user.domain.dto.UserEntityResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserLoginResponseDTO {
    private String accessToken;
    private String tokenType;
    private UserEntityResponseDTO user;
    private List<String> roles;
}
