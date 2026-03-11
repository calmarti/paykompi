package com.calmarti.paykompi.domain.order.service;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.dto.OrderResponseDto;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;
import com.calmarti.paykompi.domain.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    public UUID createOrder(CreateOrderRequestDto dto, User merchant);
    public OrderResponseDto getOrderById(UUID id, User merchant);
//    public List<OrderResponseDto> getAllOrdersByMerchantId(UUID merchantId, User merchant);
    public CustomPage<OrderResponseDto> getAllOrders(UUID merchantId, Currency currency, OrderStatus orderStatus, User user, Pageable pageable);
    public void cancelOrder(UUID orderId, User merchant);
}
