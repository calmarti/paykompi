package com.calmarti.paykompi.domain.payment.service;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.dto.PaymentResponseDto;
import com.calmarti.paykompi.domain.user.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    public UUID createPayment(CreatePaymentRequestDto dto, User payer);
    public PaymentResponseDto getPaymentById(UUID id, User user);
    public Page<PaymentResponseDto> getAllPayments(UUID accountId, Pageable pageable);
}
