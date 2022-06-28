package com.example.warehousemanagement.order.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderEntityResponseDTO {
    private Long id;
    private String orderNumber;
    private Date submittedDate;
    private Date deliveryDate;
    private boolean deleted;
    private OrderEntityOrderStatusDTO orderStatus;
    private List<OrderEntityOrderItemDTO> items;
}
