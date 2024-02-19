package com.artur.shop.order.repository;

import com.artur.shop.order.model.Order;
import com.artur.shop.order.model.OrderRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
}
