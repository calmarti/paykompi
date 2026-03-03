package com.calmarti.paykompi.domain.order.service;

import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.dto.OrderResponseDto;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    public UUID createOrder(CreateOrderRequestDto dto, User merchant);
    public OrderResponseDto getOrderById(UUID id, User merchant);
    public List<OrderResponseDto> getAllOrdersByMerchantId(UUID merchantId, User merchant);
    public void cancelOrder(UUID orderId, User merchant);
}
