package com.example.warehousemanagement.order.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderCreateDTO {
    private Long id;
    private String orderNumber;
    private Date submittedDate;
    private Date deliveryDate;
    private Long orderStatus;
    private List<OrderItemCreateDTO> items;
}
