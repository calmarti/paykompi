package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public interface PaymentService {
    public UUID createPayment(CreatePaymentRequestDto dto, User user);
}
