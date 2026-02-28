package com.calmarti.paykompi.account.dto;

import com.calmarti.paykompi.account.enums.AccountCurrency;
import com.calmarti.paykompi.account.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        String username,
        AccountCurrency currency,
        BigDecimal balance,
        BigDecimal availableBalance,
        AccountStatus accountStatus,
        Instant createdAt,
        Instant updatedAt
) {}
