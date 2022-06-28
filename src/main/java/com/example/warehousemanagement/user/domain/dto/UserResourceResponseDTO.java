package com.example.warehousemanagement.user.domain.dto;

import com.example.warehousemanagement.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class UserResourceResponseDTO {
    List<User> users;
}
