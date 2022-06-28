package com.example.warehousemanagement.order.domain;

import com.example.warehousemanagement.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = )
    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(name = "submitted_date", nullable = false)
    private Date submittedDate;

    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;

    @Column(name = "deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OrderStatus.class)
    private OrderStatus orderStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> items;
}
