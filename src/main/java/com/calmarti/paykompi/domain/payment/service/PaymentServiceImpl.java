package com.calmarti.paykompi.domain.payment.service;

import com.calmarti.paykompi.common.exception.BusinessRuleViolationException;
import com.calmarti.paykompi.common.exception.CustomAccessDeniedException;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import com.calmarti.paykompi.domain.account.repository.AccountRepository;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;
import com.calmarti.paykompi.domain.order.repository.OrderRepository;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import com.calmarti.paykompi.domain.payment.mapper.PaymentMapper;
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import com.calmarti.paykompi.domain.transaction.entity.Transaction;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.enums.UserType;
import com.calmarti.paykompi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ExternalPaymentApiSimulator externalPaymentApiSimulator;
    private final PaymentFailureService paymentFailureService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository,
                              AccountRepository accountRepository,
                              UserRepository userRepository,
                              TransactionRepository transactionRepository,
                              ExternalPaymentApiSimulator externalPaymentApiSimulator, PaymentFailureService paymentFailureService)
    {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.externalPaymentApiSimulator = externalPaymentApiSimulator;
        this.paymentFailureService = paymentFailureService;
    }

    @Override
    @Transactional
    public UUID createPayment(CreatePaymentRequestDto dto, User payer) {
        //validations:
        //ORDER
        //Order exists
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(()-> new ResourceNotFoundException(String.format("Order not found: %s", dto.orderId())));
//        Order.status == CREATED
        if (order.getOrderStatus() != OrderStatus.CREATED){
            throw new BusinessRuleViolationException("Cannot initiate payment of order: order status is not 'CREATED'");
        }
//        amount == order.amount
        if ( dto.amount().compareTo(order.getAmount()) != 0){
            throw new BusinessRuleViolationException("Payment amount does not match order amount");
        }
//        currency == order.currency
        if ( dto.paymentCurrency() != order.getCurrency()){
            throw new BusinessRuleViolationException("Payment currency does not match order currency");
        }
        //USER
//        User.status == ACTIVE
        if (payer.getUserStatus() != UserStatus.ACTIVE){
            throw new BusinessRuleViolationException("Payer status is not 'ACTIVE'");
        }
//        User.type == PERSONAL
        if (payer.getUserType() != UserType.CUSTOMER){
            throw new BusinessRuleViolationException("User type is not 'CUSTOMER'");
        }
        //ACCOUNT
//        Payer account exists
        Account account = accountRepository.findById(dto.payerAccountId())
                .orElseThrow(()-> new ResourceNotFoundException("Payer account does not exist"));
//        Payer account belongs to authenticated user
        if (! account.getUser().getId().equals(payer.getId())){
            throw new CustomAccessDeniedException("Access denied: account does not belong to the authenticated user");
        }
//        Account.status == ACTIVE
        if (account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new BusinessRuleViolationException("Account status is not 'ACTIVE'");
        }
//        Account.currency == order.currency
        if (account.getCurrency() != order.getCurrency()){
            throw new BusinessRuleViolationException("Account currency does not match order currency");
        }
//        Account.balance >= amount
        if (dto.amount().compareTo(account.getBalance()) > 0){
            throw new BusinessRuleViolationException("Insufficient account balance");
        }
//        Merchant has active account in same currency
        User merchant = order.getMerchant();
        Account merchantAccount =
                accountRepository.findByUserIdAndCurrencyAndAccountStatus(
                merchant.getId(), dto.paymentCurrency(),AccountStatus.ACTIVE)
                        .orElseThrow(
                                ()-> new ResourceNotFoundException(String.format(
                                        "Merchant account with currency %s and/or status ACTIVE not found",
                                        dto.paymentCurrency())));


//        Map to payment entity
        Payment payment = PaymentMapper.toEntity(dto, order, account);
//      Set payment_status = "CREATED"
        payment.setPaymentStatus(PaymentStatus.CREATED);
//        persist payment record
        paymentRepository.saveAndFlush(payment);

//      Simulation of approval / failure to approve
//      Execution / failure to execute;
        try {
            externalPaymentApiSimulator.approvePayment();
            payment.setPaymentStatus(PaymentStatus.APPROVED);
            executePayment(account, merchantAccount, payment, order);
        }
        catch(RuntimeException e){
            paymentFailureService.markPaymentFailed(payment.getId());
        throw e;
        }

        return payment.getId();
    }



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
        //TODO: ask GPT meaning of this "If there is no cascade from Payment, transactions must be saved."
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





//    Final Payment state possibilities
//CREATED - APPROVED - COMPLETED
//CREATED - FAILED (approval failed)
//CREATED - APPROVED - FAILED (executePayment failed for some reason)

}
