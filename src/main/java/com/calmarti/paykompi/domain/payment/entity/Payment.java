package com.calmarti.paykompi.domain.payment.entity;

import com.calmarti.paykompi.common.enums.Currency;
import com.calmarti.paykompi.domain.account.entity.Account;
import com.calmarti.paykompi.domain.order.entity.Order;
import com.calmarti.paykompi.domain.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
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
@Table(name="PAYMENTS",  check = {
        @CheckConstraint(name = "CK_accounts_amount_non_negative", constraint = "amount >= 0.01")
})

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_payments_orders"), nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payer_account_id", foreignKey = @ForeignKey(name = "FK_payments_accounts"), nullable = false)
    private Account payerAccount;
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    @Column(name = "payment_currency", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Currency paymentCurrency;
    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

}
