package com.example.warehousemanagement.user.domain.dto;

import com.example.warehousemanagement.user.domain.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCreateDTO {
    String username;
    String email;
    String password;
    List<String> roles;
}
