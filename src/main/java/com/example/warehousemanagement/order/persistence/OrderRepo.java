package com.example.warehousemanagement.order.persistence;

import com.example.warehousemanagement.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
