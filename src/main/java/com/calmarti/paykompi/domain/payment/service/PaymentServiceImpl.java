package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.domain.account.repository.AccountRepository;
import com.calmarti.paykompi.domain.order.repository.OrderRepository;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;
    private AccountRepository accountRepository;
    private UserRepository userRepository;


    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, AccountRepository accountRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID createPayment(CreatePaymentRequestDto dto, User user) {
        //validations:

//        Order exists
//        Order.status == CREATED
//        amount == order.amount
//        currency == order.currency

//        User.status == ACTIVE
//        User.type == PERSONAL

//        Payer account exists
//        Payer account belongs to authenticated user
//        Account.status == ACTIVE
//        Account.currency == order.currency
//        Account.balance >= amount
//        Merchant has active account in same currency


        return null;
    }
}
