package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    Order deleteOrder(Long orderId);
    Optional<Order> getOrderById(Long orderId);
    List<Order> getOrderByUserId(Long userId);
    List<OrderResponse> getOrders();
}
