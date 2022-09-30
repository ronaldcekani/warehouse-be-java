package com.example.warehousemanagement.product.service;

import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.graphql.service.GraphQlAwareInterface;
import com.example.warehousemanagement.core.http.PageableResponse;
import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.domain.dto.ProductCreateDTO;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService extends GraphQlAwareInterface<Product> {
    ProductEntityResponseDTO create(ProductCreateDTO productCreateDTO);

    ProductEntityResponseDTO update(Long id, ProductCreateDTO productCreateDTO);

    void delete(Long id);

    PageableResponse<ProductEntityResponseDTO, Product> getAll();

}
