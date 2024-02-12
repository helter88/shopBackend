package com.artur.shop.admin.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderStats {
    private List<Integer> labels;
    private List<BigDecimal> sales;
    private List<Long> orders;
}
