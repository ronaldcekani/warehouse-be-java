package com.example.warehousemanagement.order.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_statuses")
@Setter
@Getter
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "allowed_status_changes", nullable = false)
    private String allowedStatusChanges;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderStatus")
    private List<Order> orders;
}
