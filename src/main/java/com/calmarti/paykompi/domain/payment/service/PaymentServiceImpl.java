package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.account.repository.AccountRepository;
import com.calmarti.paykompi.domain.order.repository.OrderRepository;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UUID createPayment(CreatePaymentRequestDto dto, User payer) {
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
//        create payment record (payment_status = "CREATED")
//        persist payment record
//        executePayment(payment)



        return null;
    }


    @Override
    @Transactional
    public void executePayment(Account debitAccount, Account creditAccount, Payment payment) {

//        a. Debit payer account
//        payer.balance -= amount
//        payer.availableBalance -= amount
//
//        b. Credit merchant account
//        merchant.balance += amount
//        merchant.availableBalance += amount
//
//        c. Create Transaction (DEBIT)
//                accountId = payerAccountId
//        type = DEBIT
//        amount = amount
//        referenceType = PAYMENT
//        referenceId = payment.id
//
//        d. Create Transaction (CREDIT)
//                accountId = merchantAccountId
//        type = CREDIT
//        amount = amount
//        referenceType = PAYMENT
//        referenceId = payment.id
//
//        e. Update Payment
//        payment.status = COMPLETED
//
//        f. Update Order
//        order.status = PAID

//        If any operation a-f throws then DB transaction → rollback:
//        markPaymentFailed(paymentId)

    }

    @Override
    public void markPaymentFailed(UUID paymentId) {

    }



//    Final state possibilities
//            Success
//    Payment.status = COMPLETED
//    Order.status = PAID
//    Transactions created
//    Balances updated

//            Failure
//    Payment.status = FAILED
//    Order.status unchanged
//    No balance changes
//    No transactions created


}
