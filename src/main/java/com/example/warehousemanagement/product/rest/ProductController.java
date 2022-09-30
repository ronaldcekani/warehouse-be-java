package com.example.warehousemanagement.product.rest;

import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.domain.dto.ProductCreateDTO;
import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;
import com.example.warehousemanagement.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final JavaMailSender javaMailSender;

//    @QueryMapping
//    public Iterable<Product> products(
////            @Argument List<ColExp> where,
////            @Argument List<Object> sort,
////            @Argument Optional<Integer> page,
////            @Argument Optional<Integer> limit
//            GraphqlQueryRequest filters
//            ) {
//        return null;
////        return productService.get(where);
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setFrom("webmasterdevs2@gmail.com");
////        message.setTo("webmasterdevs2@gmail.com");
////        message.setSubject("Test");
////        message.setText("Test desc1");
////        javaMailSender.send(message);
////        return productService.getAll();
//
//
//    }

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
