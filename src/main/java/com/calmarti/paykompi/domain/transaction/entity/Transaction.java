package com.calmarti.paykompi.domain.transaction.entity;

import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.payment.entity.Payment;
import com.calmarti.paykompi.domain.transaction.enums.EntryType;
import com.calmarti.paykompi.domain.transaction.enums.Source;
import jakarta.persistence.*;
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
@JoinColumn(name="account_id", nullable = false)
private Account account;
@Column(name="entry_type", nullable = false)
@Enumerated(EnumType.STRING)
private EntryType entryType;
@Column(name = "amount", nullable = false, precision = 19, scale = 2)
private BigDecimal amount;
@Column(name = "currency", nullable = false, length = 3)
@Enumerated(EnumType.STRING)
private Currency currency;
@Column(name="source",nullable = false)
private Source source;
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name="payment_id", nullable = false)
private Payment payment;
@Column(name="created_at", nullable = false, updatable = false)
@CreationTimestamp
private Instant createdAt;

}
