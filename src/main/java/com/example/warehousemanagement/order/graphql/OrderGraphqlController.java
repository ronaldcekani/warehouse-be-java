package com.example.warehousemanagement.order.graphql;

import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.http.PageableResponse;
import com.example.warehousemanagement.order.domain.Order;
import com.example.warehousemanagement.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderGraphqlController {
    @Autowired
    OrderService orderService;

    @QueryMapping
    PageableResponse<Order, Order> orders(@Argument GraphqlQueryRequest filters) {
        return orderService.get(filters);
    }

    @QueryMapping
    Order order(@Argument Long id) {
        return orderService.find(id);
    }
}
