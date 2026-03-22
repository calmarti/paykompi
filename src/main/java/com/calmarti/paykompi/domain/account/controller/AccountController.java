package com.calmarti.paykompi.domain.account.controller;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.account.dto.AccountResponseDto;
import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import com.calmarti.paykompi.domain.account.service.AccountService;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
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
@Tag(name = "Accounts", description = "Operations to manage user accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    //requires authentication of (active) user
    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody @Valid CreateAccountRequestDto request, @AuthenticationPrincipal User auhtenticatedUser) {
        UUID id = accountService.createAccount(request, auhtenticatedUser);
        URI location = URI.create("/api/v1/accounts/" + id);
        return ResponseEntity.created(location).build();
    }

    //requires (active) user to be owner of account OR role = ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("id") UUID accountId, @AuthenticationPrincipal User user){
        AccountResponseDto response = accountService.getAccountById(accountId, user);
        return ResponseEntity.ok(response);
    }

    //restricted to role = ADMIN
    @GetMapping
    ResponseEntity<CustomPage<AccountResponseDto>>getAllAccounts(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) AccountStatus accountStatus,
            Pageable pageable
    ){
       CustomPage<AccountResponseDto> response = accountService.getAllAccounts(username, currency, accountStatus, pageable);
        return ResponseEntity.ok(response);
    }

    //restricted to role = ADMIN
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateAccountStatus(@PathVariable("id") UUID accountId, @RequestBody @Valid UpdateAccountStatusDto request){
        accountService.changeAccountStatus(accountId, request);
        return ResponseEntity.noContent().build();
    }
}
