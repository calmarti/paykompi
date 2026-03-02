package com.calmarti.paykompi.domain.order.controller;

import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.service.OrderService;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderRequestDto request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        UUID id = orderService.createOrder(request,user);
        URI location = URI.create("/api/v1/orders" + id);
        return ResponseEntity.created(location).build();
    }

    //TODO: GET /api/orders/{orderId}
    //TODO: GET /api/orders?merchantId={userId} - Get all orders created by user
    //TODO: PATCH /api/orders/{orderId}

}
