package com.calmarti.paykompi.domain.order.dto;

import com.calmarti.paykompi.common.enums.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateOrderRequestDto(
        @NotNull
        @DecimalMin(value = "0.10", inclusive = true)
        BigDecimal amount,
        @NotNull
        Currency currency,
        @Size(max = 150)
        String description
) {
}
