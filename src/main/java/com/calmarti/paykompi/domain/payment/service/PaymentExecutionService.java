package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentExecutionService {
    private final TransactionRepository transactionRepository;

    public PaymentExecutionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Transactional
    public void executePayment(Account debitAccount, Account creditAccount, Payment payment, Order order) {

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
