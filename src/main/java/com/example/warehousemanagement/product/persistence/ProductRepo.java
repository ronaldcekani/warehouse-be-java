package com.example.warehousemanagement.product.persistence;

import com.example.warehousemanagement.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
