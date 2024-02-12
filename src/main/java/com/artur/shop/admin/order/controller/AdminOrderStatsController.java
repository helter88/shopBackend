package com.artur.shop.admin.order.controller;

import com.artur.shop.admin.order.model.dto.AdminOrderStats;
import com.artur.shop.admin.order.service.AdminOrderStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/orders/statistics")
@RequiredArgsConstructor
public class AdminOrderStatsController {

    private final AdminOrderStatService adminOrderStatService;

    @GetMapping
    public AdminOrderStats getOrderStatistics(){
        return adminOrderStatService.getStatistics();
    }
}
