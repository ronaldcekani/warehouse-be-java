package com.example.warehousemanagement.order.service;

import com.example.warehousemanagement.order.domain.dto.OrderCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderEntityResponseDTO;
import com.example.warehousemanagement.order.domain.dto.OrderItemCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderStatusUpdateDTO;

import java.util.List;

public interface OrderService {
    OrderEntityResponseDTO create(OrderCreateDTO orderCreateDTO);

    OrderEntityResponseDTO updateStatus(Long idOrder, OrderStatusUpdateDTO orderStatusUpdateDTO);

    OrderEntityResponseDTO updateItems(Long idOrder, List<OrderItemCreateDTO> itemCreateDTOList);

    void removeOrderItem(Long idOrderItem);

    OrderEntityResponseDTO getById(Long id);

    List<OrderEntityResponseDTO> getAll();
}
