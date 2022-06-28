package com.example.warehousemanagement.product.domain.mapper;

import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;

public class ProductEntityMapper {
    public static ProductEntityResponseDTO entityToDtoMapper(Product product) {
        ProductEntityResponseDTO productEntityResponseDTO = new ProductEntityResponseDTO();
        productEntityResponseDTO.setId(product.getId());
        productEntityResponseDTO.setName(product.getName());
        productEntityResponseDTO.setQuantity(product.getQuantity());
        productEntityResponseDTO.setUnitPrice(product.getUnitPrice());
        productEntityResponseDTO.setVolume(product.getVolume());
        return productEntityResponseDTO;
    }
}
