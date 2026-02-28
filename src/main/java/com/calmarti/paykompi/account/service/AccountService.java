package com.calmarti.paykompi.account.service;

import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import java.util.UUID;

public interface AccountService {
   // List<AccountResponseDto> getAllAccounts()    //GET /api/v1/accounts   -> requires role = ADMIN and it needs filters & pagination
   // AccountResposeDto getAccountById(UUID id)     //GET /api/v1/accounts/{accountId}
   UUID createAccount(CreateAccountRequestDto dto);   //POST /api/v1/accounts   -> private: owned by user
   // List<AccountResponseDto> getAccountsByUser(UUID userId)  //GET GET /api/v1/accounts?userId={userId}
    // void updateAccountStatus(updateAccountStatusDto)      //PATCH /api/v1/accounts/{accountId}/status -> requires role = ADMIN
    // AccountBalanceResponseDto getAccountBalanceById       //GET /api/v1/accounts/{accountId}/balance (optional)
}
