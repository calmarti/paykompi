package com.calmarti.paykompi.account.service.impl;

import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.entity.Account;
import com.calmarti.paykompi.account.enums.AccountStatus;
import com.calmarti.paykompi.account.mapper.AccountMapper;
import com.calmarti.paykompi.account.repository.AccountRepository;
import com.calmarti.paykompi.account.service.AccountService;
import com.calmarti.paykompi.common.exception.DuplicateResourceException;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.user.entity.User;
import com.calmarti.paykompi.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

private final AccountRepository accountRepository;
private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID createAccount(CreateAccountRequestDto dto, Authentication authentication) {
        //Get principal (user) from authentication object
        User user = (User) authentication.getPrincipal();
        //Validate UK constraint: {"user_id", "currency"}
        if (accountRepository.existsByUserAndCurrency(user, dto.currency())){
            throw new DuplicateResourceException(String.format("User already has %s account",(dto.currency())));
        }
        Account account = AccountMapper.toEntity(dto, user);
        account.setBalance(BigDecimal.ZERO);
        account.setAvailableBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return AccountMapper.toResponse(account);
    }

}
