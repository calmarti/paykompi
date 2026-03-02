package com.calmarti.paykompi.domain.order.service.impl;

import com.calmarti.paykompi.common.exception.BusinessRuleViolationException;
import com.calmarti.paykompi.common.exception.CustomAccessDeniedException;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.account.repository.AccountRepository;
import com.calmarti.paykompi.domain.order.dto.CreateOrderRequestDto;
import com.calmarti.paykompi.domain.order.dto.OrderResponseDto;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;
import com.calmarti.paykompi.domain.order.mapper.OrderMapper;
import com.calmarti.paykompi.domain.order.repository.OrderRepository;
import com.calmarti.paykompi.domain.order.service.OrderService;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private AccountRepository accountRepository;

    public OrderServiceImpl(OrderRepository orderRepository, AccountRepository accountRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createOrder(CreateOrderRequestDto dto, User user) {
        // TODO: POST /api/orders
        // A BUSINESS user calls POST /api/v1/orders with an amount, currency, and description.
        // Validations:
        // 1. UserType = BUSINESS -> validated by Security Config
        // 2. Amount > 0 - > validated with @Valid by means of @DecimalMin in dto
        // 3. description's length - > validated with @Valid by means of @DecimalMin in dto
        // 4. User must have an account with accountCurrency = dto.currency
        if (! accountRepository.existsByUserAndCurrency(user,dto.currency())){
            throw new BusinessRuleViolationException("Cannot create order: user does not have an account in " + dto.currency());
        }
        // Map dto to new instance of Order
        Order order = OrderMapper.toEntity(dto, user);
        // Set default order_status = CRATED
        order.setOrderStatus(OrderStatus.CREATED);
        // Persist
        orderRepository.save(order);
        // Returns the order ID. No money moves.
        return order.getId();
    }

    @Override
    public OrderResponseDto getOrderById(UUID id, User user) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        if (!order.getUser().getId().equals(user.getId()) && ! user.getUserRole().equals(UserRole.ADMIN)){
            throw new CustomAccessDeniedException("User cannot access this order");
        }
        return OrderMapper.toResponse(order);
    }
}
