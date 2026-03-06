package com.calmarti.paykompi.domain.payment.controller;

import com.calmarti.paykompi.domain.payment.dto.CreatePaymentRequestDto;
import com.calmarti.paykompi.domain.payment.dto.PaymentResponseDto;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.payment.service.PaymentService;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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

    //TODO: GET /api/payments/{paymentId} - Only available to ADMIN
    @GetMapping("/{id}")
    ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID id){
        PaymentResponseDto response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    //TODO: GET /api/users/{userId}/payments - available to owner user and ADMIN


}