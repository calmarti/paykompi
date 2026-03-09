package com.calmarti.paykompi.domain.transaction.dto;

import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponseDto(
        UUID id,
        UUID accountId,
        UUID paymentId,
        EntryType entryType,
        BigDecimal amount,
        Currency currency,
        Source source,
        Instant createdAt
)
{}
