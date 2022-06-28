package com.example.warehousemanagement.order.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntityOrderItemDTO {
    private Long id;
    private double quantity;
    private double unitPrice;
    private double volume;
    private boolean deleted;
}
