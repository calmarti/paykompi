package com.calmarti.paykompi.domain.order.entity;

import com.calmarti.paykompi.domain.account.enums.AccountCurrency;
import com.calmarti.paykompi.domain.order.enums.OrderStatus;
import com.calmarti.paykompi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="ORDERS")
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name="merchant_id", foreignKey = @ForeignKey(name="FK_orders_users"), nullable = false)
private User user;
@Column(name = "amount", nullable = false, precision = 19, scale = 2)
private BigDecimal amount;
@Column(name = "currency", nullable = false, length = 3)
@Enumerated(EnumType.STRING)
private AccountCurrency currency;
@Column(name = "description", length = 150, nullable = false)
private String description;
@Column(name = "order_status", nullable = false)
@Enumerated(EnumType.STRING)
private OrderStatus orderStatus;
@Column(name = "created_at", nullable = false, updatable = false)
@CreationTimestamp
private Instant createdAt;
}
