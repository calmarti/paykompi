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

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    public OrderServiceImpl(OrderRepository orderRepository, AccountRepository accountRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createOrder(CreateOrderRequestDto dto, User merchant) {
        // TODO: POST /api/orders
        // A MERCHANT user calls POST /api/v1/orders with an amount, currency, and description.
        // Validations:
        // 1. UserType = MERCHANT -> validated by Security Config
        // 2. Amount > 0 - > validated with @Valid by means of @DecimalMin in dto
        // 3. description's length - > validated with @Valid by means of @DecimalMin in dto
        // 4. User must have an account with accountCurrency = dto.currency
        if (! accountRepository.existsByUserAndCurrency(merchant,dto.currency())){
            throw new BusinessRuleViolationException("Cannot create order: user does not have an account in " + dto.currency());
        }
        // Map dto to new instance of Order
        Order order = OrderMapper.toEntity(dto, merchant);
        // Set default order_status = CREATED
        order.setOrderStatus(OrderStatus.CREATED);
        // Persist
        orderRepository.save(order);
        // Returns the order ID. No money moves.
        return order.getId();
    }

    @Override
    public OrderResponseDto getOrderById(UUID orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        if (!order.getMerchant().getId().equals(user.getId()) && ! user.getUserRole().equals(UserRole.ADMIN)){
            throw new CustomAccessDeniedException("User cannot access this order");
        }
        return OrderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByMerchantId(UUID merchantId, User user) {
       //validate access: merchant.id = order.merchant_id || ROLE = ADMIN
        if (! user.getId().equals(merchantId) && ! user.getUserRole().equals(UserRole.ADMIN)){
            throw new CustomAccessDeniedException("User cannot access these orders");
        }
        //validate merchantId exists
        if (! orderRepository.existsByMerchantId(merchantId)){
            throw new ResourceNotFoundException("Order not found");
        }
        List <OrderResponseDto> orders = orderRepository.findAllByMerchantId(merchantId)
                .stream()
                .map((order)-> OrderMapper.toResponse(order))
                .toList();
        return orders;
    }

    @Override
    public void cancelOrder(UUID orderId, User merchant) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
        if (! order.getMerchant().getId().equals(merchant.getId()) && ! merchant.getUserRole().equals(UserRole.ADMIN)){
           throw new CustomAccessDeniedException("User cannot access these orders");
        }
        if (order.getOrderStatus() != OrderStatus.CREATED){
           throw new BusinessRuleViolationException("Order cannot be cancelled or is already cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
