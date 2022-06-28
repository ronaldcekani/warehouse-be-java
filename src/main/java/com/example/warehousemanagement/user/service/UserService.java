package com.example.warehousemanagement.user.service;

import com.example.warehousemanagement.user.domain.User;
import com.example.warehousemanagement.user.domain.dto.UserCreateDTO;
import com.example.warehousemanagement.user.domain.dto.UserEntityResponseDTO;

import java.util.List;

public interface UserService {
    UserEntityResponseDTO create(UserCreateDTO userCreateDTO);

    UserEntityResponseDTO update(Long id, UserCreateDTO userCreateDTO);

    List<User> getAllUsers();

    UserEntityResponseDTO getById(Long id);

}
