package com.example.warehousemanagement.user.domain.dto;
import lombok.Data;

import java.util.List;

@Data
public class UserEntityResponseDTO {
    Long id;
    String username;
    List<UserRoleResponseDTO> roles;
}
