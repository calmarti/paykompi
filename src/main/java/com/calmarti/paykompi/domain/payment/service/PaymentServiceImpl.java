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
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.enums.UserType;
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
        //ORDER
        //Order exists
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(()-> new ResourceNotFoundException(String.format("Order not found: %s", dto.orderId())));
//        Order.status == CREATED
        if (order.getOrderStatus() != OrderStatus.CREATED){
            throw new BusinessRuleViolationException("Cannot initiate payment of order: order status is not 'CREATED'");
        }
//        amount == order.amount
        if (! dto.amount().equals(order.getAmount())){
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
        if (! accountRepository.existsByUserIdAndCurrencyAndAccountStatus(
                merchant.getId(), account.getCurrency(),AccountStatus.ACTIVE)
        ) {
            throw new BusinessRuleViolationException("Merchant account status is not 'ACTIVE' or its currency does not match");
        }
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
