package com.calmarti.paykompi.domain.account.repository;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import com.calmarti.paykompi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByUserAndCurrency(User user, Currency currency);
    boolean existsByUserIdAndCurrencyAndAccountStatus(UUID userId, Currency currency, AccountStatus accountStatus);
}
