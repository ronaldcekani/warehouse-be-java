package com.example.warehousemanagement.order.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntityOrderStatusDTO {
    private Long id;
    private String name;
    private String allowedStatusChanges;
}
