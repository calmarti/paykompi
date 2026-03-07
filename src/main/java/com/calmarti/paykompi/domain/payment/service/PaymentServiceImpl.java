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
import com.calmarti.paykompi.domain.payment.dto.PaymentResponseDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import com.calmarti.paykompi.domain.payment.mapper.PaymentMapper;
import com.calmarti.paykompi.domain.payment.repository.PaymentRepository;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserRole;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.enums.UserType;
import com.calmarti.paykompi.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
   // private final TransactionRepository transactionRepository;
    private final ExternalPaymentApiSimulator externalPaymentApiSimulator;
    private final PaymentFailureService paymentFailureService;
    private final PaymentExecutionService paymentExecutionService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository,
                              AccountRepository accountRepository,
                              UserRepository userRepository,
                              TransactionRepository transactionRepository,
                              ExternalPaymentApiSimulator externalPaymentApiSimulator, PaymentFailureService paymentFailureService, PaymentExecutionService paymentExecutionService)
    {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        //this.transactionRepository = transactionRepository;
        this.externalPaymentApiSimulator = externalPaymentApiSimulator;
        this.paymentFailureService = paymentFailureService;
        this.paymentExecutionService = paymentExecutionService;
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
            paymentExecutionService.executePayment(account, merchantAccount, payment, order);
        }
        catch(RuntimeException exception){
            paymentFailureService.markPaymentFailed(payment.getId());
        //TODO: only ExternalPaymentException is being handled (BAD_GATEWAY), all other execution errors will throw 500
        throw exception;
        }

        return payment.getId();
    }

    //    Final Payment state possibilities
//CREATED - APPROVED - COMPLETED
//CREATED - FAILED (approval failed)
//CREATED - APPROVED - FAILED (executePayment failed for some reason)



    @Override
    public PaymentResponseDto getPaymentById(UUID id, User user) {
        log.debug(">>> getPaymentById called, user role: {}", user.getUserRole());
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        if (! payment.getPayerAccount().getUser().getId().equals(user.getId()) && ! user.getUserRole().equals(UserRole.ADMIN)) {
            throw new CustomAccessDeniedException("User cannot access this account");
        }
        return PaymentMapper.toResponse(payment);
    }

    @Override
    //TODO: page = 1 & size = 4 returns empty; page = 1 & size = 3 returns 1 object, why?
    public Page<PaymentResponseDto> getAllPayments(UUID accountId, Pageable pageable) {
        Page<Payment> paginatedPayment;
        if (accountId != null){
           paginatedPayment = paymentRepository.findByPayerAccount_Id(accountId, pageable);
        }
        else{
           paginatedPayment = paymentRepository.findAll(pageable);
        }
        return paginatedPayment.map((payment)-> PaymentMapper.toResponse(payment));
    }
}