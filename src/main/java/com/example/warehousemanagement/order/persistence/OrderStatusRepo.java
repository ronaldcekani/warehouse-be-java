package com.example.warehousemanagement.order.persistence;

import com.example.warehousemanagement.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {
    OrderStatus findOneByName(String name);
}
