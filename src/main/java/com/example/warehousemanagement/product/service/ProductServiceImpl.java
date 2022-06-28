package com.example.warehousemanagement.product.service;

import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.domain.dto.ProductCreateDTO;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;
import com.example.warehousemanagement.product.domain.mapper.ProductEntityMapper;
import com.example.warehousemanagement.product.persistence.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Override
    public ProductEntityResponseDTO create(ProductCreateDTO productCreateDTO) {
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setQuantity(productCreateDTO.getQuantity());
        product.setUnitPrice(productCreateDTO.getUnitPrice());
        product.setVolume(productCreateDTO.getVolume());
        Product savedProduct = productRepo.save(product);

        return ProductEntityMapper.entityToDtoMapper(savedProduct);
    }

    @Override
    public ProductEntityResponseDTO update(Long id, ProductCreateDTO productCreateDTO) {
        Product product = productRepo.findById(id).orElseThrow();
        product.setName(productCreateDTO.getName());
        product.setQuantity(productCreateDTO.getQuantity());
        product.setUnitPrice(productCreateDTO.getUnitPrice());
        product.setVolume(productCreateDTO.getVolume());
        Product savedProduct = productRepo.save(product);

        return ProductEntityMapper.entityToDtoMapper(savedProduct);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepo.findById(id).orElseThrow();
        product.setDeleted(true);
        productRepo.save(product);
    }

    public List<ProductEntityResponseDTO> getAll() {
        // TODO: add pagination | query deleted products
        List<Product> products = productRepo.findAll();
        return products.stream().filter(product -> !product.isDeleted()).map(ProductEntityMapper::entityToDtoMapper).toList();
    }
}
