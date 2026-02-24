package com.calmarti.paykompi.user.service.impl;

public interface AccountService {
   // List<AccountResponseDto> getAllAccounts()    //GET /api/v1/accounts   -> requires role = ADMIN and it needs filters & pagination
   // AccountResposeDto getAccountById(UUID id)     //GET /api/v1/accounts/{accountId}
   // Account createAccount(UUID id, createAccountRequestDto)   //POST /api/v1/accounts   -> private: owned by user
   // List<AccountResponseDto> getAccountsByUser(UUID userId)  //GET GET /api/v1/accounts?userId={userId}
    // void updateAccountStatus(updateAccountStatusDto)      //PATCH /api/v1/accounts/{accountId}/status -> requires role = ADMIN
    // AccountBalanceResponseDto getAccountBalanceById       //GET /api/v1/accounts/{accountId}/balance (optional)
}
