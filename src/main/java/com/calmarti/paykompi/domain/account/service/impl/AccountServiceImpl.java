package com.calmarti.paykompi.domain.account.service.impl;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.common.exception.*;
import com.calmarti.paykompi.domain.account.dto.AccountResponseDto;
import com.calmarti.paykompi.domain.account.dto.CreateAccountRequestDto;
import com.calmarti.paykompi.domain.account.dto.UpdateAccountStatusDto;
import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import com.calmarti.paykompi.domain.account.mapper.AccountMapper;
import com.calmarti.paykompi.domain.account.repository.AccountRepository;
import com.calmarti.paykompi.domain.account.service.AccountService;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserRole;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
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
    public UUID createAccount(CreateAccountRequestDto dto, User authenticatedUser) {
        //Validate UK constraint: {"user_id", "currency"}
        if (accountRepository.existsByUserAndCurrency(authenticatedUser, dto.currency())) {
            throw new DuplicateResourceException(String.format("User already has %s account", (dto.currency())));
        }
        Account account = AccountMapper.toEntity(dto, authenticatedUser);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(AccountStatus.ACTIVE);
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
    public void changeAccountStatus(UUID accountId, UpdateAccountStatusDto dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found"));
        if (account.getAccountStatus().equals(AccountStatus.CLOSED)){
            throw new BusinessRuleViolationException("Invalid transition: cannot activate / freeze a closed account");
        }
        accountRepository.save(AccountMapper.updateAccountStatusInEntity(account, dto));
    }

    @Override
    public CustomPage<AccountResponseDto> getAllAccounts(String username, Currency currency, AccountStatus accountStatus, Pageable pageable) {

        Specification<Account> spec = Specification.allOf();

        if (username != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("username"), username));
        }
        if (currency != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("currency"), currency));
        }
        if (accountStatus != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("accountStatus"), accountStatus));
        }

        Page<Account> paginatedAccount = accountRepository.findAll(spec, pageable);
        Page<AccountResponseDto> mappedPaginatedAccount = paginatedAccount.map(AccountMapper::toResponse);
        CustomPage<AccountResponseDto> customPaginatedAcccount = new CustomPage<>(mappedPaginatedAccount);
        return customPaginatedAcccount;
    }
}
