package com.calmarti.paykompi.account.mapper;

import com.calmarti.paykompi.account.dto.AccountResponseDto;
import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.entity.Account;
import com.calmarti.paykompi.user.entity.User;

import java.util.UUID;

public class AccountMapper {

    public static Account toEntity(CreateAccountRequestDto dto, User user) {
        Account account = new Account();
        account.setUser(user);
        account.setCurrency(dto.currency());
        return account;
    }

    public static UUID toResponse(Account account){
        return account.getId();
    }
}
