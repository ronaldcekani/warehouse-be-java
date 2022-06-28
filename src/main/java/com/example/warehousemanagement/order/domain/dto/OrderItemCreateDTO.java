package com.example.warehousemanagement.order.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCreateDTO {
    private Long id;
    private double quantity;
}
