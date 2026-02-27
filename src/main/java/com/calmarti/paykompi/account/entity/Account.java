package com.calmarti.paykompi.account.entity;

import com.calmarti.paykompi.account.enums.AccountStatus;
import com.calmarti.paykompi.account.enums.AccountCurrency;
import com.calmarti.paykompi.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "ACCOUNTS",
        uniqueConstraints =
        @UniqueConstraint(name = "UK_accounts_user_id_currency", columnNames = {"user_id", "currency"}),
        check = {
                @CheckConstraint(name = "CK_accounts_balance_non_negative", constraint = "balance >= 0"),
                @CheckConstraint(name = "CK_accounts_available_balance_non_negative", constraint = "available_balance >= 0"),
                @CheckConstraint(name = "CK_accounts_available_balance_lte_balance", constraint = "available_balance <= balance")
        })
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_accounts_users"), nullable = false)
    private User user;
    @Column(name = "currency", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private AccountCurrency currency;
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;
    @Column(name = "available_balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal availableBalance;
    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}
