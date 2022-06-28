package com.example.warehousemanagement.product.service;

import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.domain.dto.ProductCreateDTO;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;

public interface ProductService {
    ProductEntityResponseDTO create(ProductCreateDTO productCreateDTO);

    ProductEntityResponseDTO update(Long id, ProductCreateDTO productCreateDTO);

    void delete(Long id);
}
