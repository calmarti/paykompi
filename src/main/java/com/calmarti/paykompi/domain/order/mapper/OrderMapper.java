package com.calmarti.paykompi.domain.order.mapper;

import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.dto.OrderResponseDto;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public class OrderMapper {

    public static Order toEntity(CreateOrderRequestDto dto, User merchant){
        Order order = new Order();
        order.setMerchant(merchant);
        order.setAmount(dto.amount());
        order.setCurrency(dto.currency());
        order.setDescription(dto.description());
        return order;
    }

    public static OrderResponseDto toResponse(Order order){
        return new OrderResponseDto(
                order.getId(),
                order.getMerchant().getId(),
                order.getAmount(),
                order.getCurrency(),
                order.getDescription(),
                order.getOrderStatus(),
                order.getCreatedAt());
    }
}
