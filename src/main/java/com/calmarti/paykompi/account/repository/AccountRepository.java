package com.calmarti.paykompi.account.repository;

import com.calmarti.paykompi.account.entity.Account;
import com.calmarti.paykompi.account.enums.AccountCurrency;
import com.calmarti.paykompi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByUserAndCurrency(User user, AccountCurrency currency);
}
