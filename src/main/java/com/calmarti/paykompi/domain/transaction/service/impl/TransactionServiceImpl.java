package com.calmarti.paykompi.domain.transaction.service.impl;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;
import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import com.calmarti.paykompi.domain.transaction.mapper.TransactionMapper;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import com.calmarti.paykompi.domain.transaction.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public CustomPage<TransactionResponseDto> getAllTransactions(
            UUID accountId,
            UUID paymentId,
            Source source,
            EntryType entryType,
            Pageable pageable) {

        Specification<Transaction> spec = Specification.allOf();

        if (accountId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("account").get("id"), accountId));
        }

        if (paymentId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("payment").get("id"), paymentId));
        }

        if (source != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("source"), source));
        }

        if (entryType != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("entryType"), entryType));
        }

        Page<TransactionResponseDto> paginatedTransaction = transactionRepository.findAll(spec, pageable)
                .map(TransactionMapper::toResponse);

        return new CustomPage<TransactionResponseDto>(paginatedTransaction);
    }
}
