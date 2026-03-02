package com.calmarti.paykompi.domain.order.mapper;

import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public class OrderMapper {

    public static Order toEntity(CreateOrderRequestDto dto, User user){
        Order order = new Order();
        order.setUser(user);
        order.setAmount(dto.amount());
        order.setCurrency(dto.currency());
        order.setDescription(dto.description());
        return order;
    }
}
