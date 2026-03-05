package com.calmarti.paykompi.domain.transaction.entity;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


@Entity
@Table(name="TRANSACTIONS")

public class Transaction {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name="account_id",  foreignKey = @ForeignKey(name = "FK_transactions_accounts"), nullable = false, updatable = false)
private Account account;
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name="payment_id",  foreignKey = @ForeignKey(name = "FK_transactions_payments"), nullable = false, updatable = false)
private Payment payment;
@Column(name="entry_type", nullable = false, updatable = false)
@Enumerated(EnumType.STRING)
private EntryType entryType;
@Column(name = "amount", nullable = false, precision = 19, scale = 2, updatable = false)
@DecimalMin(value = "0.01", inclusive = true)
private BigDecimal amount;
@Column(name = "currency", nullable = false, updatable = false)
@Enumerated(EnumType.STRING)
private Currency currency;
@Column(name="source",nullable = false, updatable = false)
@Enumerated(EnumType.STRING)
private Source source;
@CreationTimestamp
private Instant createdAt;

}
