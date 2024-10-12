package com.project.shopapp.controller;

import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderDetails")
public class OrderDetailController {
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("id") long id){
        return ResponseEntity.status(200).body("Get order details with id: " + id);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderDetailsByOrderId(@Valid @PathVariable("id") long id){
        return ResponseEntity.status(200).body("Get order details with order id: " + id);
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrderDetails(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.status(200).body("Create order details");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetails(
            @Valid @PathVariable("id") long id
            ,@Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        return ResponseEntity.status(200).body("Update order details");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetails(@Valid @PathVariable("id") long id){
        return ResponseEntity.status(200).body("Delete order details");
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrderDetailsByOrderId(@Valid @PathVariable("id") long id){
        return ResponseEntity.status(200).body("Delete order details by order id: " + id);
    }
}
