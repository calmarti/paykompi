package com.calmarti.paykompi.domain.payment.repository;

import com.calmarti.paykompi.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> , JpaSpecificationExecutor<Payment> {
    public Page<Payment> findByPayerAccount_Id(UUID accountId, Pageable pageable);

}
