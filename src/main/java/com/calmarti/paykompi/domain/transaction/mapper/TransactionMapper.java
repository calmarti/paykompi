package com.calmarti.paykompi.domain.transaction.mapper;

import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;
import com.calmarti.paykompi.domain.transaction.entity.Transaction;

public class TransactionMapper {

    public static TransactionResponseDto toResponse(Transaction transaction){
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getPayment().getId(),
                transaction.getEntryType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getSource(),
                transaction.getCreatedAt()
        );
    }

}
