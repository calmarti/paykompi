package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.dto.PaymentResponseDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public interface PaymentService {
    public UUID createPayment(CreatePaymentRequestDto dto, User payer);
    public PaymentResponseDto getPaymentById(UUID id, User user);
}
