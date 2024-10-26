package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElse(null);
        orderDetail.setOrder(order);
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return orderDetails;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if(orderDetail != null){
            orderDetail.setColor(orderDetailDTO.getColor());
            orderDetail.setPrice(orderDetailDTO.getPrice());
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
            Product product = productRepository.findById(orderDetailDTO.getProductId()).orElse(null);
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
            return orderDetailRepository.save(orderDetail);
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteOrderDetail(long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }
}
