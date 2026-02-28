package com.calmarti.paykompi.account.service;

import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.UUID;

public interface AccountService {
    UUID createAccount(CreateAccountRequestDto dto, Authentication authentication);   //POST /api/v1/accounts   -> private: requires user to be owner of account
    // AccountResposeDto getAccountById(UUID id)     //GET /api/v1/accounts/{accountId} -> requires user to be owner of account OR role = ADMIN
   // List<AccountResponseDto> getAllAccounts()    //GET /api/v1/accounts   -> requires role = ADMIN and it needs filters & pagination
   // List<AccountResponseDto> getAccountsByUser(UUID userId)  //GET /api/v1/accounts?userId={userId}  -> requires user to be owner of account OR role = ADMIN
    // void updateAccountStatus(updateAccountStatusDto)      //PATCH /api/v1/accounts/{accountId}/status -> requires role = ADMIN
    // AccountBalanceResponseDto getAccountBalanceById       //GET /api/v1/accounts/{accountId}/balance (optional)  -> requires user to be owner of account OR role = ADMIN
}
