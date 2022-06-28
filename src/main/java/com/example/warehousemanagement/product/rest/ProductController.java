package com.example.warehousemanagement.product.rest;

import com.example.warehousemanagement.product.domain.dto.ProductCreateDTO;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;
import com.example.warehousemanagement.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product/create")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ProductEntityResponseDTO create(@RequestBody ProductCreateDTO productCreateDTO) {
        return productService.create(productCreateDTO);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ProductEntityResponseDTO update(@PathVariable Long id, @RequestBody ProductCreateDTO productCreateDTO) {
        return productService.update(id, productCreateDTO);
    }
}
