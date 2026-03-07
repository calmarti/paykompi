package com.calmarti.paykompi.domain.payment.controller;

import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.dto.PaymentResponseDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.service.PaymentService;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    ResponseEntity<Void> createPayment(@RequestBody @Valid CreatePaymentRequestDto request, @AuthenticationPrincipal User user){
      UUID id = paymentService.createPayment(request, user);
      URI location = URI.create("/api/v1/payments/" + id);
      return ResponseEntity.created(location).build();
    }

    //TODO: GET /api/payments/{paymentId} - Only available to owner payer and to ADMIN
    @GetMapping("/{id}")
    ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID id, @AuthenticationPrincipal User user){
        PaymentResponseDto response = paymentService.getPaymentById(id, user);
        return ok(response);
    }

    //TODO: GET /api/users/ (all payments with pagination and ?payerAccountId={userId) - available only to ADMIN
    @GetMapping
    ResponseEntity<Page<PaymentResponseDto>> getAllPayments(@RequestParam(required = false) UUID accountId, Pageable pageable ){
        Page<PaymentResponseDto> response = paymentService.getAllPayments(accountId, pageable);
        return ResponseEntity.ok(response);
    }



}