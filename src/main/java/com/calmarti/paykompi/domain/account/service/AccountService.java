package com.calmarti.paykompi.domain.account.service;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.account.dto.AccountResponseDto;
import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import com.calmarti.paykompi.domain.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface AccountService {

    UUID createAccount(CreateAccountRequestDto dto, User user);

    AccountResponseDto getAccountById(UUID accountId, User user);

    CustomPage<AccountResponseDto> getAllAccounts(String username, Currency currency, AccountStatus accountStatus, Pageable pageable);

    void changeAccountStatus(UUID accountId, UpdateAccountStatusDto dto);

}
