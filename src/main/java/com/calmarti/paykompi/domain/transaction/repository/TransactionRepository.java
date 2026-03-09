package com.calmarti.paykompi.domain.transaction.repository;

import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TransactionRepository extends
        JpaRepository<Transaction, UUID> ,
        JpaSpecificationExecutor<Transaction> {
}
