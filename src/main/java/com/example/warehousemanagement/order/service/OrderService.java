package com.example.warehousemanagement.order.service;

import com.example.warehousemanagement.core.graphql.service.GraphQlAwareInterface;
import com.example.warehousemanagement.order.domain.Order;
import com.example.warehousemanagement.order.domain.dto.OrderCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderEntityResponseDTO;
import com.example.warehousemanagement.order.domain.dto.OrderItemCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderStatusUpdateDTO;

import java.util.List;

public interface OrderService extends GraphQlAwareInterface<Order> {
    OrderEntityResponseDTO create(OrderCreateDTO orderCreateDTO);

    OrderEntityResponseDTO updateStatus(Long idOrder, OrderStatusUpdateDTO orderStatusUpdateDTO);

    OrderEntityResponseDTO updateItems(Long idOrder, List<OrderItemCreateDTO> itemCreateDTOList);

    void removeOrderItem(Long idOrderItem);

    OrderEntityResponseDTO getById(Long id);

    List<OrderEntityResponseDTO> getAll();
}
