package com.example.warehousemanagement.core.graphql.service;

import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.http.PageableResponse;

public interface GraphQlAwareInterface<E> {
    PageableResponse<E, E> get(GraphqlQueryRequest filters);

    E find(Long id);
}
