package com.calmarti.paykompi.domain.order.dto;

import com.calmarti.paykompi.domain.account.enums.AccountCurrency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderRequestDto(
        @NotNull
        @DecimalMin(value = "0.10", inclusive = true)
        BigDecimal amount,
        @NotNull
        AccountCurrency currency,
        String description
) {
}
