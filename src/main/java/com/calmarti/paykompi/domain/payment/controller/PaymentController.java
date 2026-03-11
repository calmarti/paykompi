package com.calmarti.paykompi.domain.payment.controller;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.dto.PaymentResponseDto;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import com.calmarti.paykompi.domain.payment.service.PaymentService;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //restricted to users with status = ACTIVE && type = CUSTOMER
    @PostMapping
    ResponseEntity<Void> createPayment(@RequestBody @Valid CreatePaymentRequestDto request, @AuthenticationPrincipal User user){
      UUID id = paymentService.createPayment(request, user);
      URI location = URI.create("/api/v1/payments/" + id);
      return ResponseEntity.created(location).build();
    }

    //GET /api/v1/payments/{paymentId} - Only available to owner payer and to ADMIN
    @GetMapping("/{id}")
    ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID id, @AuthenticationPrincipal User user){
        PaymentResponseDto response = paymentService.getPaymentById(id, user);
        return ok(response);
    }

    //restricted to ADMIN
    @GetMapping
    ResponseEntity<CustomPage<PaymentResponseDto>> getAllPayments(
            @RequestParam(required = false) UUID payerAccountId,
            @RequestParam(required = false) UUID orderId,
            @RequestParam(required = false) Currency paymentCurrency,
            @RequestParam(required = false)PaymentStatus paymentStatus,
            Pageable pageable ){
        CustomPage<PaymentResponseDto> response = paymentService.getAllPayments(
                payerAccountId, orderId, paymentCurrency, paymentStatus, pageable);
        return ResponseEntity.ok(response);
    }



}