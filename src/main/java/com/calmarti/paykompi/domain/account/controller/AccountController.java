package com.calmarti.paykompi.domain.account.controller;

import com.calmarti.paykompi.domain.account.dto.AccountResponseDto;
import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.domain.account.service.AccountService;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


//For endpoints available to users with role = USER, access requires status = ACTIVE
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    //requires authentication of (active) user
    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody @Valid CreateAccountRequestDto request, Authentication authentication) {
        //Get principal (user) from authentication object
        User user = (User) authentication.getPrincipal();
        UUID id = accountService.createAccount(request, user);
        URI location = URI.create("/api/v1/accounts/" + id);
        return ResponseEntity.created(location).build();
    }

    //requires (active) user to be owner of account OR role = ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("id") UUID accountId, @AuthenticationPrincipal User user){
        AccountResponseDto response = accountService.getAccountById(accountId, user);
        return ResponseEntity.ok(response);
    }

    //TODO: implement filters and pagination
    //restricted to role = ADMIN
    @GetMapping
    ResponseEntity<List<AccountResponseDto>>getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    //restricted to role = ADMIN
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateAccountStatus(@PathVariable("id") UUID accountId, @RequestBody @Valid UpdateAccountStatusDto request){
        accountService.changeAccountStatus(accountId, request);
        return ResponseEntity.noContent().build();
    }
}
