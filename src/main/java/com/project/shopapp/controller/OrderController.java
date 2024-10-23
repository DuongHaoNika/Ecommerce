package com.project.shopapp.controller;
import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;
import com.project.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            OrderResponse orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok().body(orderResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable long id){
        try {
            Optional<Order> order = orderService.getOrderById(id);
            return ResponseEntity.status(200).body(order);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable long userId){
        try {
            List<Order> orders = orderService.getOrderByUserId(userId);
            return ResponseEntity.status(200).body(orders);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok().body(updatedOrder);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id){
        Order order = orderService.deleteOrder(id);
        if(order != null) {
            return ResponseEntity.ok().body("Deleted order by id: " + id);
        }
        else {
            return ResponseEntity.badRequest().body("Order ID is not found!");
        }
    }
}
