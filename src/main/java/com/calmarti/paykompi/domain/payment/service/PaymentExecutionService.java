package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.account.repository.AccountRepository;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;
import com.calmarti.paykompi.domain.order.repository.OrderRepository;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentExecutionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public PaymentExecutionService(TransactionRepository transactionRepository, AccountRepository accountRepository, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void executePayment(UUID debitAccountId, UUID creditAccountId, UUID paymentId, UUID orderId) {
        Account debitAccount = accountRepository.findById(debitAccountId)
                .orElseThrow(()-> new ResourceNotFoundException("Debit account not found"));
        Account creditAccount = accountRepository.findById(creditAccountId)
                .orElseThrow(()-> new ResourceNotFoundException("Credit account not found"));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));

//        a. Debit payer account
        debitAccount.setBalance(debitAccount.getBalance().subtract(payment.getAmount()));
//
//        b. Credit merchant account
        creditAccount.setBalance(creditAccount.getBalance().add(payment.getAmount()));
//
//        c. Create Transaction (DEBIT)
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(debitAccount);
        debitTransaction.setPayment(payment);
        debitTransaction.setEntryType(EntryType.DEBIT);
        debitTransaction.setAmount(payment.getAmount());
        debitTransaction.setCurrency(payment.getPaymentCurrency());
        debitTransaction.setSource(Source.PAYMENT);
        transactionRepository.save(debitTransaction);

//        d. Create Transaction (CREDIT)
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(creditAccount);
        creditTransaction.setPayment(payment);
        creditTransaction.setEntryType(EntryType.CREDIT);
        creditTransaction.setAmount(payment.getAmount());
        creditTransaction.setCurrency(payment.getPaymentCurrency());
        creditTransaction.setSource(Source.PAYMENT);
        transactionRepository.save(creditTransaction);

//        e. Update Payment status
        payment.setPaymentStatus(PaymentStatus.COMPLETED);

//        f. Update Order status
        order.setOrderStatus(OrderStatus.PAID);

//        If any operation a-f throws then DB transaction → rollback and payment is marked as 'FAILED'
    }
}
