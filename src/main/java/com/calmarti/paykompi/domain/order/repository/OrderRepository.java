package com.calmarti.paykompi.domain.order.repository;

import com.calmarti.paykompi.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> , JpaSpecificationExecutor<Order> {
    public boolean existsByMerchantId(UUID merchantId);
    public List<Order> findAllByMerchantId(UUID merchantId);
}
