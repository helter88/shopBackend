package com.artur.shop.admin.order.service;

import com.artur.shop.admin.order.model.AdminOrder;
import com.artur.shop.admin.order.model.AdminOrderStatus;
import com.artur.shop.admin.order.repository.AdminOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminExportService {

    private final AdminOrderRepository adminOrderRepository;
    public List<AdminOrder> exportOrders(LocalDateTime from, LocalDateTime to, AdminOrderStatus orderStatus) {
        return adminOrderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(from, to, orderStatus);
    }
}
