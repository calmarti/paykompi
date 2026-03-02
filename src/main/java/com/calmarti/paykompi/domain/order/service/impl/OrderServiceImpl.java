package com.calmarti.paykompi.domain.order.service.impl;

import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.repository.OrderRepository;
import com.calmarti.paykompi.domain.order.service.OrderService;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public UUID createOrder(CreateOrderRequestDto dto, User user) {
        return UUID.fromString("OK");
    }
}
