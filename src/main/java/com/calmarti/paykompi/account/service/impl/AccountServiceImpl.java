package com.calmarti.paykompi.account.service.impl;

import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.repository.AccountRepository;
import com.calmarti.paykompi.account.service.AccountService;

import java.util.UUID;

public class AccountServiceImpl implements AccountService {

private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createAccount(CreateAccountRequestDto dto) {
        return null;
    }
}
