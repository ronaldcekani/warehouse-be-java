package com.example.warehousemanagement.core.graphql.persistence;

import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.http.PageableResponse;

import javax.persistence.EntityNotFoundException;

public interface GraphqlGenericRepo<E> {
    PageableResponse<E, E> filter(GraphqlQueryRequest filters, Class<E> cls);

    E find(Long id, Class<E> cls) throws EntityNotFoundException;
}
