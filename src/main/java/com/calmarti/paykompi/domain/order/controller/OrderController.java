package com.calmarti.paykompi.domain.order.controller;

import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.dto.OrderResponseDto;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.service.OrderService;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid CreateOrderRequestDto request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        UUID id = orderService.createOrder(request,user);
        URI location = URI.create("/api/v1/orders/" + id);
        return ResponseEntity.created(location).build();
    }

    //GET /api/orders/{orderId}  - Restricted to order owner and ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable(name="id") UUID orderId, @AuthenticationPrincipal User merchant){
        OrderResponseDto order = orderService.getOrderById(orderId, merchant);
        return ResponseEntity.ok(order);
    }

    //GET /api/orders?merchantId={userId} - Get all orders created by user - Restricted to order owner and ADMIN
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByMerchantId(@RequestParam UUID merchantId, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(orderService.getAllOrdersByMerchantId(merchantId, user));
    }

    //PATCH /api/v1/orders/{orderId}/cancel  - Restricted to order owner and ADMIN
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID id, @AuthenticationPrincipal User merchant){
        orderService.cancelOrder(id, merchant);
        return ResponseEntity.noContent().build();
    }

}
