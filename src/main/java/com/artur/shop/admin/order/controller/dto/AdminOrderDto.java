package com.artur.shop.admin.order.controller.dto;

import com.artur.shop.admin.order.model.AdminOrderStatus;
import com.artur.shop.common.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminOrderDto {
    private Long id;
    private LocalDateTime placeDate;
    private OrderStatus orderStatus;
    private BigDecimal grossValue;
}
