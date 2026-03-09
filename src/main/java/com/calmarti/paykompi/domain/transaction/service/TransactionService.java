package com.calmarti.paykompi.domain.transaction.service;

import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;

import java.util.UUID;

public interface TransactionService {
    public TransactionResponseDto getTransactionById(UUID transactionId);
}
