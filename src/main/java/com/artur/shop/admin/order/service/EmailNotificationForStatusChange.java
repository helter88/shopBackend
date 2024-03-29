package com.artur.shop.admin.order.service;

import com.artur.shop.admin.order.model.AdminOrder;
import com.artur.shop.admin.order.model.AdminOrderStatus;
import com.artur.shop.common.mail.EmailClientService;
import com.artur.shop.common.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmailNotificationForStatusChange {

    private final EmailClientService emailClientService;
    void sendEmailNotification(OrderStatus newStatus, AdminOrder adminOrder) {
        String title = "Order " + adminOrder.getId() + " changes status to: " + newStatus;
        if(newStatus == OrderStatus.PROCESSING){
            sendEmail(adminOrder.getEmail(), title, AdminOrderEmailMessage.createContent(newStatus, adminOrder.getId()));
        } else if (newStatus == OrderStatus.COMPLETED) {
            sendEmail(adminOrder.getEmail(), title, AdminOrderEmailMessage.createContent(newStatus, adminOrder.getId()));
        } else if (newStatus == OrderStatus.REFUND) {
            sendEmail(adminOrder.getEmail(), title, AdminOrderEmailMessage.createContent(newStatus, adminOrder.getId()));
        }
    }

    private void sendEmail(String email, String title, String content) {
        emailClientService.getInstance().send(email, title, content);
    }
}
