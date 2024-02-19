package com.artur.shop.admin.order.service;

import com.artur.shop.common.model.OrderStatus;

public class AdminOrderEmailMessage {
    public static String createContent(OrderStatus newStatus, Long id) {
        if (newStatus == OrderStatus.PROCESSING){
            return "Your order: " + id + " is processing." +
                    "\nStatus was change to " + newStatus +
                    "\nYour order is processing by our team"+
                    "\nWe will forward it to send after completing"+
                    "\nBest regards" +
                    "\nShop";
        } else if (newStatus == OrderStatus.COMPLETED) {
            return "Your order: " + id + " is completed." +
                    "\nStatus was change to " + newStatus +
                    "\nYour order is processing by our team"+
                    "\nThank you for your purchase"+
                    "\nBest regards" +
                    "\nShop";
        }
        return "Your order: " + id + " is returned." +
                "\nStatus was change to " + newStatus +
                "\nBest regards" +
                "\nShop";
    }
}
