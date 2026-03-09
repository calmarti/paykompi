package com.calmarti.paykompi.domain.transaction.service.impl;

import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;
import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import com.calmarti.paykompi.domain.transaction.mapper.TransactionMapper;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import com.calmarti.paykompi.domain.transaction.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponseDto getTransactionById(UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction not found"));
           return TransactionMapper.toResponse(transaction);
    }
}
