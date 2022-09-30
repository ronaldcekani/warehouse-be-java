package com.example.warehousemanagement.product.persistence;

import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.product.domain.Product;

import java.util.List;

public interface ProductRepoCustom {
    List<Product> filter(List<ColExp> where);
}
