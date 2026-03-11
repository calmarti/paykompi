package com.calmarti.paykompi.domain.order.service.impl;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.enums.Currency;
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
import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserRole;
import com.calmarti.paykompi.domain.user.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        // A MERCHANT user calls POST /api/v1/orders with an amount, currency, and description.
        // Validations:
        // 1. UserType = MERCHANT
        if (merchant.getUserType() != UserType.MERCHANT){
            throw new BusinessRuleViolationException("User type is not MERCHANT");
        }
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
    public CustomPage<OrderResponseDto> getAllOrders(UUID merchantId, Currency currency,
                                                     OrderStatus orderStatus,
                                                     User user, Pageable pageable) {
       //validate access: merchant.id = order.merchant_id || ROLE = ADMIN
        if (! user.getId().equals(merchantId) && ! user.getUserRole().equals(UserRole.ADMIN)){
            throw new CustomAccessDeniedException("User cannot access these orders");
        }

        Specification<Order> spec = Specification.allOf();

        if (merchantId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("merchant").get("id"), merchantId));
        }

        if (currency != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("currency"), currency));
        }

        if (orderStatus!= null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("orderStatus"), orderStatus));
        }

        Page<OrderResponseDto> paginatedOrder = orderRepository.findAll(spec, pageable)
                .map(OrderMapper::toResponse);
        CustomPage<OrderResponseDto> customPaginatedOrder = new CustomPage<>(paginatedOrder);

        return customPaginatedOrder;
    }

    @Override
    public void cancelOrder(UUID orderId, User merchant) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
        if (! order.getMerchant().getId().equals(merchant.getId()) && ! merchant.getUserRole().equals(UserRole.ADMIN)){
           throw new CustomAccessDeniedException("User cannot access this order");
        }
        if (order.getOrderStatus() != OrderStatus.CREATED){
           throw new BusinessRuleViolationException("Order cannot be cancelled or is already cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
