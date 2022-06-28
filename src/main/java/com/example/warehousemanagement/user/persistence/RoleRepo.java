package com.example.warehousemanagement.user.persistence;

import com.example.warehousemanagement.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query(value = "SELECT * FROM roles WHERE name IN (:roleNames)", nativeQuery = true)
    List<Role> findAllByName(List<String> roleNames);
}
