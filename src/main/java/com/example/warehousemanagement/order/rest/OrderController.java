package com.example.warehousemanagement.order.rest;

import com.example.warehousemanagement.order.domain.Order;
import com.example.warehousemanagement.order.domain.OrderItem;
import com.example.warehousemanagement.order.domain.OrderStatus;
import com.example.warehousemanagement.order.domain.constants.OrderStatusEnum;
import com.example.warehousemanagement.order.domain.dto.OrderCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderEntityResponseDTO;
import com.example.warehousemanagement.order.domain.dto.OrderItemCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderStatusUpdateDTO;
import com.example.warehousemanagement.order.persistence.OrderRepo;
import com.example.warehousemanagement.order.persistence.OrderStatusRepo;
import com.example.warehousemanagement.order.service.OrderService;
import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.persistence.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/order/create")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public OrderEntityResponseDTO create(@RequestBody OrderCreateDTO orderCreateDto) {
        return orderService.create(orderCreateDto);
    }

    @PatchMapping("/order/{idOrder}/status/update")
    public OrderEntityResponseDTO updateStatus(@PathVariable Long idOrder, @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO) {
        return orderService.updateStatus(idOrder, orderStatusUpdateDTO);
    }
}
