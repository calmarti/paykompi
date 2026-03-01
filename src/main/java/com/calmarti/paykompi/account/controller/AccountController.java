package com.calmarti.paykompi.account.controller;

import com.calmarti.paykompi.account.dto.AccountResponseDto;
import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.account.service.AccountService;
import com.calmarti.paykompi.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody @Valid CreateAccountRequestDto request, Authentication authentication) {
        //Get principal (user) from authentication object
        User user = (User) authentication.getPrincipal();
        UUID id = accountService.createAccount(request, user);
        URI location = URI.create("/api/v1/accounts/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("id") UUID accountId, @AuthenticationPrincipal User user){
        AccountResponseDto response = accountService.getAccountById(accountId, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<AccountResponseDto>>getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateAccountStatus(@PathVariable("id") UUID accountId, @RequestBody @Valid UpdateAccountStatusDto request){
        accountService.updateAccountStatus(accountId, request);
        return ResponseEntity.noContent().build();
    }
}
