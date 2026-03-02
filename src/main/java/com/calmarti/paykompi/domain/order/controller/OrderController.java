package com.calmarti.paykompi.domain.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    // TODO: POST /api/orders
    // A BUSINESS user calls POST /api/v1/orders with an amount, currency, and description.
    // The system validates: BUSINESS type, amount > 0, merchant has account with that currency, description's length
    // Set default order_status = CRATED
    // Returns the order ID. No money moves.
    //TODO: GET /api/orders/{orderId}
    //TODO: GET /api/orders?merchantId={userId} - Get all orders created by user
    //TODO: PATCH /api/orders/{orderId}

}
