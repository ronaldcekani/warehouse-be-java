package com.example.warehousemanagement.user.rest;

import com.example.warehousemanagement.user.domain.User;
import com.example.warehousemanagement.user.domain.dto.UserCreateDTO;
import com.example.warehousemanagement.user.domain.dto.UserEntityResponseDTO;
import com.example.warehousemanagement.user.domain.dto.UserResourceResponseDTO;
import com.example.warehousemanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResourceResponseDTO> getUsers() {
        UserResourceResponseDTO userResourceResponseDTO = new UserResourceResponseDTO();
        userResourceResponseDTO.setUsers(userService.getAllUsers());
        return ResponseEntity.ok().body(userResourceResponseDTO);
    }

    @GetMapping("/user/{id}")
    public UserEntityResponseDTO getUser(@PathVariable() Long id) {
        UserEntityResponseDTO userEntityResponseDTO = userService.getById(id);
        return userEntityResponseDTO;
    }

    @PostMapping("/user/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserEntityResponseDTO> create(@RequestBody UserCreateDTO userCreateDTO) {
        UserEntityResponseDTO user = userService.create(userCreateDTO);
        return ResponseEntity.ok().body(user);
    }
}
