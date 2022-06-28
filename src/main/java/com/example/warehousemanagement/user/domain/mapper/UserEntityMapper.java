package com.example.warehousemanagement.user.domain.mapper;

import com.example.warehousemanagement.user.domain.Role;
import com.example.warehousemanagement.user.domain.User;
import com.example.warehousemanagement.user.domain.dto.UserCreateDTO;
import com.example.warehousemanagement.user.domain.dto.UserEntityResponseDTO;
import com.example.warehousemanagement.user.domain.dto.UserRoleResponseDTO;
import com.example.warehousemanagement.user.persistence.RoleRepo;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserEntityMapper {
    public static UserEntityResponseDTO entityToDtoMapper(User user) {
        UserEntityResponseDTO responseDTO = new UserEntityResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        List<UserRoleResponseDTO> userRoleResponse = user.getRoles().stream().map(role -> {
            UserRoleResponseDTO userRoleResponseDTO = new UserRoleResponseDTO();
            userRoleResponseDTO.setId(role.getId());
            userRoleResponseDTO.setName(role.getName());
            return userRoleResponseDTO;
        }).toList();
        responseDTO.setRoles(userRoleResponse);
        return responseDTO;
    }

    public static User dtoToEntityMapper(UserCreateDTO dto, @Nullable User user, @Nullable List<Role> roles) {
        if (user == null) {
            user = new User();
        }

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles(roles);
        return user;
    }

    public static List<UserRoleResponseDTO> userRolesMapper(List<Role> roles) {
        return roles.stream().map(role -> {
            UserRoleResponseDTO userRoleResponseDTO = new UserRoleResponseDTO();
            userRoleResponseDTO.setId(role.getId());
            userRoleResponseDTO.setName(role.getName());
            return userRoleResponseDTO;
        }).toList();
    }
}
