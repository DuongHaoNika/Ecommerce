package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CommonsLog
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Shipping Date?");
        }
        order.setActive(true);
        order.setShippingDate(shippingDate);
        orderRepository.save(order);
        modelMapper.typeMap(Order.class, OrderResponse.class);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order ID?"));
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
            return order;
        }
        else {
            return null;
        }
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<OrderResponse> getOrders() {
        return List.of();
    }
}
