package com.calmarti.paykompi.domain.order.service;

import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public interface OrderService {
    public UUID createOrder(CreateOrderRequestDto dto, User user);
}
