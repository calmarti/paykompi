package com.calmarti.paykompi.domain.transaction.controller;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.domain.transaction.dto.TransactionResponseDto;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import com.calmarti.paykompi.domain.transaction.service.TransactionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //GET transaction by ID - available only to role = ADMIN
    @GetMapping("{id}")
    ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable("id") UUID transactionId) {
        TransactionResponseDto response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }

    //GET all transaction with URL query params: accountId, paymentID and pagination params - - available only to role = ADMIN
    @GetMapping
    ResponseEntity<CustomPage<TransactionResponseDto>> getAllTransactions(
            @RequestParam(required = false) UUID accountId,
            @RequestParam(required = false) UUID paymentId,
            @RequestParam(required=false) EntryType entryType,
            @RequestParam(required=false) Source source,
            Pageable pageable){

            CustomPage<TransactionResponseDto> response = transactionService.getAllTransactions(accountId, paymentId, source, entryType, pageable);

            return ResponseEntity.ok(response);
    }



}