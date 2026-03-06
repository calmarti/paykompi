package com.calmarti.paykompi.domain.payment.dto;

import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

import java.util.UUID;

public record PaymentResponseDto(
    @NotNull
    UUID id,
    @NotNull
    UUID orderId,
    UUID payerAccountId,
    BigDecimal amount,
    Currency currency,
    PaymentStatus paymentStatus,
    Instant createdAt
) {
}
