package com.artur.shop.admin.order.controller;

import com.artur.shop.admin.order.controller.dto.AdminOrderDto;
import com.artur.shop.admin.order.controller.mapper.AdminOrderMapper;
import com.artur.shop.admin.order.model.AdminOrder;
import com.artur.shop.admin.order.model.AdminOrderStatus;
import com.artur.shop.admin.order.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public Page<AdminOrderDto> getOrders(Pageable pageable) {
        return AdminOrderMapper.mapToPageDtos(adminOrderService.getOrders(pageable));
    }

    @GetMapping("/{id}")
    public AdminOrder getOrder(@PathVariable Long id) {
        return adminOrderService.getOrder(id);
    }

    @PatchMapping("/{id}")
    public void changeOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> status){
        adminOrderService.changeOrderStatus(id, status);
    }

    @GetMapping("/statuses")
    public List<String> getStatuses(){
        return Arrays.stream(AdminOrderStatus.values())
                .map(Enum::name)
                .toList();
    }
}
