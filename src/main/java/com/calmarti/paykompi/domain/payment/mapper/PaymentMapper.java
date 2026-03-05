package com.calmarti.paykompi.domain.payment.mapper;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;

public class PaymentMapper {

    public static Payment toEntity(CreatePaymentRequestDto dto, Order order, Account account){
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPayerAccount(account);
        payment.setAmount(dto.amount());
        payment.setPaymentCurrency(dto.paymentCurrency());
        return payment;
    }
}
