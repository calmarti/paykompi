package com.calmarti.paykompi.domain.transaction.service;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
    public TransactionResponseDto getTransactionById(UUID transactionId);
    public CustomPage<TransactionResponseDto> getAllTransactions(
            UUID accountId, UUID paymentId,
            Source source, EntryType entryType,
            Pageable pageable);
}
