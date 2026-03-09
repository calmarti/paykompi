package com.calmarti.paykompi.domain.transaction.controller;

import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;
import com.calmarti.paykompi.domain.transaction.repository.TransactionRepository;
import com.calmarti.paykompi.domain.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //TODO: GET transaction by ID - avaliable only to role = ADMIN
    @GetMapping("{id}")
    ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable("id") UUID transactionId) {
        TransactionResponseDto response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }

//TODO: GET all with URL query params: accountId, paymentID and pagination params - - avaliable only to role = ADMIN

}