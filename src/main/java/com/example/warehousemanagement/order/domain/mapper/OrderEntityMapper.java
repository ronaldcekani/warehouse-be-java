package com.example.warehousemanagement.order.domain.mapper;

import com.example.warehousemanagement.order.domain.OrderItem;
import com.example.warehousemanagement.order.domain.OrderStatus;
import com.example.warehousemanagement.order.domain.dto.OrderEntityOrderItemDTO;
import com.example.warehousemanagement.order.domain.dto.OrderEntityOrderStatusDTO;

import java.util.List;

public class OrderEntityMapper {
    public static List<OrderEntityOrderItemDTO> mapItemsForResponse(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> {
            OrderEntityOrderItemDTO entityOrderItemDTO = new OrderEntityOrderItemDTO();
            entityOrderItemDTO.setId(orderItem.getId());
            entityOrderItemDTO.setQuantity(orderItem.getQuantity());
            entityOrderItemDTO.setVolume(orderItem.getVolume());
            entityOrderItemDTO.setUnitPrice(orderItem.getUnitPrice());
            return entityOrderItemDTO;
        }).toList();
    }

    public static OrderEntityOrderStatusDTO mapStatusForResponse(OrderStatus status){
        OrderEntityOrderStatusDTO orderEntityOrderStatusDTO = new OrderEntityOrderStatusDTO();
        orderEntityOrderStatusDTO.setId(status.getId());
        orderEntityOrderStatusDTO.setName(status.getName());
        orderEntityOrderStatusDTO.setAllowedStatusChanges(status.getAllowedStatusChanges());
        return orderEntityOrderStatusDTO;
    }
}
