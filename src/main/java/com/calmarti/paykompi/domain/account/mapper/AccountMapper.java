package com.calmarti.paykompi.domain.account.mapper;

import com.calmarti.paykompi.domain.account.dto.AccountIdResponseDto;
import com.calmarti.paykompi.domain.account.dto.AccountResponseDto;
import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.user.entity.User;

public class AccountMapper {

    public static Account toEntity(CreateAccountRequestDto dto, User user) {
        Account account = new Account();
        account.setUsername(user.getUsername());
        account.setUser(user);
        account.setCurrency(dto.currency());
        return account;
    }

    public static AccountIdResponseDto toIdResponse(Account account){
        return new AccountIdResponseDto(account.getId());
    }

    public static AccountResponseDto toResponse(Account account){
        return new AccountResponseDto(
                account.getId(),
                account.getUsername(),
                account.getCurrency(),
                account.getBalance(),
                account.getAvailableBalance(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }

    public static Account updateAccountStatusInEntity(Account account, UpdateAccountStatusDto dto){
        account.setStatus(dto.accountStatus());
        return account;
    }
}
