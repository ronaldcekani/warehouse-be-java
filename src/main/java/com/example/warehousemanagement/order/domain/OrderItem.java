package com.example.warehousemanagement.order.domain;

import com.example.warehousemanagement.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
