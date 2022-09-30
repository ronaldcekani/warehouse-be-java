package com.example.warehousemanagement.product.service;

import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.graphql.persistence.GraphqlGenericRepo;
import com.example.warehousemanagement.core.http.PageableResponse;
import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.domain.dto.ProductCreateDTO;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;
import com.example.warehousemanagement.product.domain.mapper.ProductEntityMapper;
import com.example.warehousemanagement.product.persistence.ProductRepo;
import com.example.warehousemanagement.product.persistence.ProductRepoCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.warehousemanagement.product.persistence.specification.ProductSpecification.isProductDeleted;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductRepoCustom productRepoCustom;

    @Autowired
    GraphqlGenericRepo<Product> graphqlGenericRepo;

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

    public PageableResponse<ProductEntityResponseDTO, Product> getAll() {
        // TODO: add pagination | query deleted products
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepo.findAll(isProductDeleted(), pageable);
        List<ProductEntityResponseDTO> content = products.getContent().stream().map(ProductEntityMapper::entityToDtoMapper).toList();
        return new PageableResponse<ProductEntityResponseDTO, Product>(
                content,
                products
        );
    }

    @Override
    public PageableResponse<Product, Product> get(GraphqlQueryRequest filters) {
        return graphqlGenericRepo.filter(filters, Product.class);
    }

    @Override
    public Product find(Long id) {
        return graphqlGenericRepo.find(id, Product.class);
    }
}
