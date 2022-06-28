package com.example.warehousemanagement.product.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleted = false;
}
