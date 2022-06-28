package com.example.warehousemanagement.order.service;

import com.example.warehousemanagement.order.domain.Order;
import com.example.warehousemanagement.order.domain.OrderItem;
import com.example.warehousemanagement.order.domain.OrderStatus;
import com.example.warehousemanagement.order.domain.dto.OrderCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderEntityResponseDTO;
import com.example.warehousemanagement.order.domain.dto.OrderItemCreateDTO;
import com.example.warehousemanagement.order.domain.dto.OrderStatusUpdateDTO;
import com.example.warehousemanagement.order.domain.mapper.OrderEntityMapper;
import com.example.warehousemanagement.order.persistence.OrderItemRepo;
import com.example.warehousemanagement.order.persistence.OrderRepo;
import com.example.warehousemanagement.order.persistence.OrderStatusRepo;
import com.example.warehousemanagement.product.domain.Product;
import com.example.warehousemanagement.product.persistence.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderStatusRepo orderStatusRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Override
    public OrderEntityResponseDTO create(OrderCreateDTO orderCreateDto) {
        // TODO: refactor this it has duplicate code
        Order order = new Order();
        // TODO: hard coded CREATED move it into a constant
        OrderStatus orderStatus = orderStatusRepo.findOneByName("CREATED");
        order.setOrderNumber(orderCreateDto.getOrderNumber());
        order.setDeliveryDate(orderCreateDto.getDeliveryDate());
        order.setOrderStatus(orderStatus);
        order.setSubmittedDate(new Date());
        List<Long> orderItemIds = orderCreateDto.getItems().stream().map(OrderItemCreateDTO::getId).toList();
        List<Product> products = productRepo.findAllById(orderItemIds);
        List<OrderItem> orderItems = orderCreateDto.getItems().stream().map(itemCreateDTO -> {
            Product product = products.stream().filter(item -> item.getId().equals(itemCreateDTO.getId())).findFirst().orElseThrow();
            OrderItem orderItem = new OrderItem();
            orderItem.setUnitPrice(product.getUnitPrice());
            orderItem.setQuantity(itemCreateDTO.getQuantity());
            orderItem.setVolume(product.getVolume());
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            return orderItem;
        }).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);
        OrderEntityResponseDTO responseDTO = new OrderEntityResponseDTO();
        responseDTO.setId(savedOrder.getId());
        responseDTO.setOrderStatus(OrderEntityMapper.mapStatusForResponse(savedOrder.getOrderStatus()));
        responseDTO.setItems(OrderEntityMapper.mapItemsForResponse(savedOrder.getItems().stream().filter(orderItem -> !orderItem.isDeleted()).toList()));
        responseDTO.setDeliveryDate(savedOrder.getDeliveryDate());
        responseDTO.setSubmittedDate(savedOrder.getSubmittedDate());
        return responseDTO;
    }

    @Override
    public OrderEntityResponseDTO updateStatus(Long idOrder, OrderStatusUpdateDTO orderStatusUpdateDTO) {
        // TODO: refactor duplicated code
        Order order = orderRepo.findById(idOrder).orElseThrow();
        OrderStatus orderStatus = orderStatusRepo.findById(orderStatusUpdateDTO.getStatus()).orElseThrow();
        // check status transition
        boolean canChangeStatus = Arrays.stream(order.getOrderStatus().getAllowedStatusChanges().split("|"))
                .anyMatch(allowedStatus -> allowedStatus.equals(orderStatus.getName()));
        if (!canChangeStatus) {
            throw new IllegalStateException("Can not transition from order status: " + order.getOrderStatus().getName() + " to order status: " + orderStatus.getName());
        }

        order.setOrderStatus(orderStatus);
        Order savedOrder = orderRepo.save(order);
        OrderEntityResponseDTO responseDTO = new OrderEntityResponseDTO();
        responseDTO.setId(savedOrder.getId());
        responseDTO.setOrderStatus(OrderEntityMapper.mapStatusForResponse(savedOrder.getOrderStatus()));
        responseDTO.setItems(OrderEntityMapper.mapItemsForResponse(savedOrder.getItems().stream().filter(orderItem -> !orderItem.isDeleted()).toList()));
        responseDTO.setDeliveryDate(savedOrder.getDeliveryDate());
        responseDTO.setSubmittedDate(savedOrder.getSubmittedDate());
        return responseDTO;
    }

    @Override
    public OrderEntityResponseDTO updateItems(Long idOrder, List<OrderItemCreateDTO> itemCreateDTOList) {
        // TODO: refactor this it has duplicate code
        Order order = orderRepo.findById(idOrder).orElseThrow();
        List<Long> orderItemIds = itemCreateDTOList.stream().map(OrderItemCreateDTO::getId).toList();
        List<Product> products = productRepo.findAllById(orderItemIds);
        List<OrderItem> orderItems = itemCreateDTOList.stream().map(itemCreateDTO -> {
            Product product = products.stream().filter(item -> item.getId().equals(itemCreateDTO.getId())).findFirst().orElseThrow();
            OrderItem orderItem = new OrderItem();
            orderItem.setUnitPrice(product.getUnitPrice());
            orderItem.setQuantity(itemCreateDTO.getQuantity());
            orderItem.setVolume(product.getVolume());
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            return orderItem;
        }).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);
        OrderEntityResponseDTO responseDTO = new OrderEntityResponseDTO();
        responseDTO.setId(savedOrder.getId());
        responseDTO.setOrderStatus(OrderEntityMapper.mapStatusForResponse(savedOrder.getOrderStatus()));
        responseDTO.setItems(OrderEntityMapper.mapItemsForResponse(savedOrder.getItems().stream().filter(orderItem -> !orderItem.isDeleted()).toList()));
        responseDTO.setDeliveryDate(savedOrder.getDeliveryDate());
        responseDTO.setSubmittedDate(savedOrder.getSubmittedDate());
        return responseDTO;
    }

    @Override
    public void removeOrderItem(Long idOrderItem) {
        OrderItem orderItem = orderItemRepo.findById(idOrderItem).orElseThrow();
        orderItem.setDeleted(true);
        orderItemRepo.save(orderItem);
    }

    @Override
    public OrderEntityResponseDTO getById(Long id) {
        // TODO: refactor it has duplicate code
        Order order = orderRepo.findById(id).orElseThrow();
        OrderEntityResponseDTO responseDTO = new OrderEntityResponseDTO();
        responseDTO.setId(order.getId());
        responseDTO.setOrderStatus(OrderEntityMapper.mapStatusForResponse(order.getOrderStatus()));
        responseDTO.setItems(OrderEntityMapper.mapItemsForResponse(order.getItems().stream().filter(orderItem -> !orderItem.isDeleted()).toList()));
        responseDTO.setDeliveryDate(order.getDeliveryDate());
        responseDTO.setSubmittedDate(order.getSubmittedDate());
        return responseDTO;
    }

    @Override
    public List<OrderEntityResponseDTO> getAll() {
        // TODO: refactor this needs pagination | duplicated code
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(order -> {
            OrderEntityResponseDTO responseDTO = new OrderEntityResponseDTO();
            responseDTO.setId(order.getId());
            responseDTO.setOrderStatus(OrderEntityMapper.mapStatusForResponse(order.getOrderStatus()));
            responseDTO.setItems(OrderEntityMapper.mapItemsForResponse(order.getItems().stream().filter(orderItem -> !orderItem.isDeleted()).toList()));
            responseDTO.setDeliveryDate(order.getDeliveryDate());
            responseDTO.setSubmittedDate(order.getSubmittedDate());
            return responseDTO;
        }).toList();
    }
}
