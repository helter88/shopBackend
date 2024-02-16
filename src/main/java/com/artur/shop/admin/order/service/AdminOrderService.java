package com.artur.shop.admin.order.service;

import com.artur.shop.admin.order.model.AdminOrder;
import com.artur.shop.admin.order.model.AdminOrderLog;
import com.artur.shop.admin.order.model.AdminOrderStatus;
import com.artur.shop.admin.order.repository.AdminOrderLogRepository;
import com.artur.shop.admin.order.repository.AdminOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final AdminOrderRepository adminOrderRepository;
    private final AdminOrderLogRepository adminOrderLogRepository;
    private final EmailNotificationForStatusChange emailNotificationForStatusChange;

    public Page<AdminOrder> getOrders(Pageable pageable) {
        return adminOrderRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("id").descending())
        );
    }

    public AdminOrder getOrder(Long id) {
        return adminOrderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void changeOrderStatus(Long id, Map<String, String> status) {
        AdminOrder adminOrder = adminOrderRepository.findById(id).orElseThrow();
        patchOrderStatus(adminOrder, status);
    }

    private void patchOrderStatus(AdminOrder adminOrder, Map<String, String> status) {
        if (status.get("orderStatus") != null) {
            AdminOrderStatus oldStatus = adminOrder.getOrderStatus();
            AdminOrderStatus newStatus = AdminOrderStatus.valueOf(status.get("orderStatus"));
            if(oldStatus == newStatus){
                return;
            }
            adminOrder.setOrderStatus(newStatus);
            logStatusChange(adminOrder.getId(), oldStatus, newStatus);
            emailNotificationForStatusChange.sendEmailNotification(newStatus,adminOrder);
        }
    }

    private void logStatusChange(Long orderId, AdminOrderStatus oldStatus, AdminOrderStatus newStatus) {
        adminOrderLogRepository.save(AdminOrderLog.builder()
                        .created(LocalDateTime.now())
                        .orderId(orderId)
                        .note("Status change from " + oldStatus.name() + " to " + newStatus.name())
                .build());
    }
}