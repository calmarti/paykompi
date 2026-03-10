package com.calmarti.paykompi.domain.account.service;

import com.calmarti.paykompi.domain.account.dto.AccountResponseDto;
import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.domain.user.entity.User;

import java.util.List;
import java.util.UUID;


public interface AccountService {

    UUID createAccount(CreateAccountRequestDto dto, User user);

    AccountResponseDto getAccountById(UUID accountId, User user);

    List<AccountResponseDto> getAllAccounts();

    void changeAccountStatus(UUID accountId, UpdateAccountStatusDto dto);

}
