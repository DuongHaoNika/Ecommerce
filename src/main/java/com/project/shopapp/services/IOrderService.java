package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO);
    OrderResponse updateOrder(Long orderId, OrderDTO orderDTO);
    OrderResponse deleteOrder(Long orderId);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrders();
}
