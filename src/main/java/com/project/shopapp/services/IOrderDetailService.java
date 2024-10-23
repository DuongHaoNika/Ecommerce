package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetail getOrderDetail(long orderDetailId);
    List<OrderDetail> getOrderDetailByOrderId(long orderId);
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(long orderDetailId);
}
