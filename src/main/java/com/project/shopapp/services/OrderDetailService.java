package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
        modelMapper.typeMap(OrderDetail.class, OrderDetail.class);
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
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
            modelMapper.typeMap(OrderDetailDTO.class, OrderDetail.class);
            modelMapper.map(orderDetailDTO, orderDetail);
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
