package com.calmarti.paykompi.domain.account.entity;

import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
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
        })
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="username", nullable=false)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_accounts_users"), nullable = false)
    private User user;
    @Column(name = "currency", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @PositiveOrZero
    private BigDecimal balance;
    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}
