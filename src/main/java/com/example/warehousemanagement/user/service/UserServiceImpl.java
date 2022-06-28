package com.example.warehousemanagement.user.service;

import com.example.warehousemanagement.user.domain.Role;
import com.example.warehousemanagement.user.domain.User;
import com.example.warehousemanagement.user.domain.dto.UserCreateDTO;
import com.example.warehousemanagement.user.domain.dto.UserEntityResponseDTO;
import com.example.warehousemanagement.user.domain.mapper.UserEntityMapper;
import com.example.warehousemanagement.user.persistence.RoleRepo;
import com.example.warehousemanagement.user.persistence.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntityResponseDTO create(UserCreateDTO userCreateDTO) {
        List<Role> roles = roleRepo.findAllByName(userCreateDTO.getRoles());
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User user = UserEntityMapper.dtoToEntityMapper(userCreateDTO, null, roles);
        return UserEntityMapper.entityToDtoMapper(userRepo.save(user));
    }

    @Override
    public UserEntityResponseDTO update(Long id, UserCreateDTO userCreateDTO) {
        User currentUser = userRepo.findById(id).orElseThrow();
        List<Role> roles = roleRepo.findAllByName(userCreateDTO.getRoles());
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User user = UserEntityMapper.dtoToEntityMapper(userCreateDTO, currentUser, roles);
        return UserEntityMapper.entityToDtoMapper(userRepo.save(user));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserEntityResponseDTO getById(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        return UserEntityMapper.entityToDtoMapper(user);
    }
}
