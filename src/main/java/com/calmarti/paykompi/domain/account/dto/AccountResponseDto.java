package com.calmarti.paykompi.domain.account.dto;

import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.account.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        String username,
        Currency currency,
        BigDecimal balance,
        BigDecimal availableBalance,
        AccountStatus accountStatus,
        Instant createdAt,
        Instant updatedAt
) {}
