package com.calmarti.paykompi.account.service.impl;

import com.calmarti.paykompi.account.dto.AccountResponseDto;
import com.calmarti.paykompi.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.account.entity.Account;
import com.calmarti.paykompi.account.enums.AccountStatus;
import com.calmarti.paykompi.account.mapper.AccountMapper;
import com.calmarti.paykompi.account.repository.AccountRepository;
import com.calmarti.paykompi.account.service.AccountService;
import com.calmarti.paykompi.common.exception.CustomAccessDeniedException;
import com.calmarti.paykompi.common.exception.DuplicateResourceException;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.user.entity.User;
import com.calmarti.paykompi.user.enums.UserRole;
import com.calmarti.paykompi.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


import static java.util.stream.Collectors.toList;


@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID createAccount(CreateAccountRequestDto dto, User user) {
        //Validate UK constraint: {"user_id", "currency"}
        if (accountRepository.existsByUserAndCurrency(user, dto.currency())) {
            throw new DuplicateResourceException(String.format("User already has %s account", (dto.currency())));
        }
        Account account = AccountMapper.toEntity(dto, user);
        account.setBalance(BigDecimal.ZERO);
        account.setAvailableBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return AccountMapper.toIdResponse(account).id();
    }

    @Override
    public AccountResponseDto getAccountById(UUID accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!account.getUser().getId().equals(user.getId()) && ! user.getUserRole().equals(UserRole.ADMIN)) {
            throw new CustomAccessDeniedException("User cannot access this account");
        }

        return AccountMapper.toResponse(account);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        List<AccountResponseDto> accounts = accountRepository.findAll()
                .stream()
                .map(((account) -> AccountMapper.toResponse(account)))
                .collect(toList());
        return accounts;
    }
}
