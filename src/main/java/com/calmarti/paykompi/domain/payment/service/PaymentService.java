package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.UUID;

public interface PaymentService {
    public UUID createPayment(CreatePaymentRequestDto dto, User payer);
    public void executePayment(Account debitAccount, Account creditAccount, Payment payment);
    public void markPaymentFailed(UUID paymentId);
}
