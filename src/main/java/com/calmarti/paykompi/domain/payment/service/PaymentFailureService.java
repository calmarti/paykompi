package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class PaymentFailureService {

    private final PaymentRepository paymentRepository;

    public PaymentFailureService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    //markPaymentFailed must run in a new transaction (because the failure may come from a rolled-back transaction)
    @Transactional(propagation = REQUIRES_NEW)
    public void markPaymentFailed(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        payment.setPaymentStatus(PaymentStatus.FAILED);
    }
}
