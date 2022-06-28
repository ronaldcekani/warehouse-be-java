package com.example.warehousemanagement.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEntityResponseDTO {
    private Long id;
    private String name;
    private double quantity;
    private double unitPrice;
    private double volume;
    private boolean deleted;
}
