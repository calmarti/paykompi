package com.calmarti.paykompi.domain.payment.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record CreatePaymentRequestDto(
   @NotNull
   UUID orderId,
   @NotNull
   UUID payerAccountId,
   @NotNull(message = "Amount is required")
   @DecimalMin(value = "0.10", inclusive = true, message = "Amount must be greater than 0.10")
   //@DecimalMax(value = "10000000000000000.00", message = "Amount exceeds maximum limit")
   BigDecimal amount,
   @NotNull(message="Currency should be EUR, USD, GBP or CHF")
   Currency currency
    ) {
}
