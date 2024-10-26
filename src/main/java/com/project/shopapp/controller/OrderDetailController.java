package com.project.shopapp.controller;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderDetails")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("id") long id){
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.status(200).body(orderDetail);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@Valid @PathVariable("id") long id){
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrderId(id);
        return ResponseEntity.status(200).body(orderDetails);
    }

    @PostMapping()
    public ResponseEntity<?> createOrderDetails(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.status(200).body("Created order details");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetails(
            @Valid @PathVariable("id") long id
            ,@Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
        return ResponseEntity.status(200).body(orderDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetails(@Valid @PathVariable("id") long id){
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.status(200).body("Deleted order detail with id: " + id);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrderDetailsByOrderId(@Valid @PathVariable("id") long id){
        return ResponseEntity.status(200).body("Delete order details by order id: " + id);
    }
}
