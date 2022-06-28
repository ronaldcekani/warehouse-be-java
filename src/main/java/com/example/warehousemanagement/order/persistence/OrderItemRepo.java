package com.example.warehousemanagement.order.persistence;

import com.example.warehousemanagement.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {}
