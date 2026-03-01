package com.calmarti.paykompi.account.service;

import com.calmarti.paykompi.account.dto.AccountResponseDto;
import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.user.entity.User;

import java.util.List;
import java.util.UUID;


//TODO: Business rule -> Restrict actions to userStatus = ACTIVE
public interface AccountService {
    UUID createAccount(CreateAccountRequestDto dto, User user);   //POST /api/v1/accounts   -> private: requires user to be owner of account
    //TODO: Include authorization for ROLE = ADMIN, regardless of user
    AccountResponseDto getAccountById(UUID accountId, User user);   //GET /api/v1/accounts/{accountId} -> requires user to be owner of account OR role = ADMIN
    //TODO: pagination and filters
    List<AccountResponseDto> getAllAccounts();    //GET /api/v1/accounts   -> requires role = ADMIN and it needs filters & pagination
    //TODO: Restrict to valida transitions
    void updateAccountStatus(UUID accountId, UpdateAccountStatusDto dto);     //PATCH /api/v1/accounts/{accountId}/status -> requires role = ADMIN
    // List<AccountResponseDto> getAccountsByUser(User user)  //GET /api/v1/accounts?userId={userId}  -> requires user to be owner of account OR role = ADMIN
    // AccountBalanceResponseDto getAccountBalanceById       //GET /api/v1/accounts/{accountId}/balance (optional)  -> requires user to be owner of account OR role = ADMIN
}
