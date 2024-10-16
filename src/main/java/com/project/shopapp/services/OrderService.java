package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) {
        Order newOrder = Order
                .builder()
                .email(orderDTO.getEmail())
                .build();
        return null; // sau code not
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderResponse deleteOrder(Long orderId) {
        return null;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrders() {
        return List.of();
    }
}
