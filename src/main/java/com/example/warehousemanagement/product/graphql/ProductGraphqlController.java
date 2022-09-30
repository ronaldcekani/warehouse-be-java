package com.example.warehousemanagement.product.graphql;

import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.http.PageableResponse;
import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductGraphqlController {

    @Autowired
    protected ProductService productService;

    @QueryMapping
    public PageableResponse<Product, ?> products(@Argument GraphqlQueryRequest filters) {
        return productService.get(filters);
    }

    @QueryMapping Product product(@Argument Long id) {
        return productService.find(id);
    }
}
