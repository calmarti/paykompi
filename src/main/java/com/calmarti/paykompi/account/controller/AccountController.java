package com.calmarti.paykompi.account.controller;

import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody @Valid CreateAccountRequestDto request, Authentication authentication) {
        UUID id = accountService.createAccount(request, authentication);
        URI location = URI.create("/api/v1/accounts/" + id);
        return ResponseEntity.created(location).build();
    }


}
