package com.calmarti.paykompi.domain.payment.repository;

import com.calmarti.paykompi.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
