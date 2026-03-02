package com.calmarti.paykompi.domain.order.dto;

import com.calmarti.paykompi.domain.account.enums.AccountCurrency;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponseDto(
        UUID id,
        UUID merchant_id,
        BigDecimal amount,
        AccountCurrency currency,
        String description,
        OrderStatus orderStatus,
        Instant createdAt
) {
}
