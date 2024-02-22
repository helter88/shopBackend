package com.artur.shop.order.service.mapper;

import com.artur.shop.common.model.Cart;
import com.artur.shop.common.model.CartItem;
import com.artur.shop.order.model.Order;
import com.artur.shop.order.model.OrderDto;
import com.artur.shop.order.model.OrderRow;
import com.artur.shop.common.model.OrderStatus;
import com.artur.shop.order.model.OrderSummary;
import com.artur.shop.order.model.Payment;
import com.artur.shop.order.model.Shipment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {
    public static Order createNewOrder(OrderDto orderDto, Cart cart, Shipment shipment, Payment payment, Long userId) {
        return Order.builder()
                .firstname(orderDto.firstname())
                .lastname(orderDto.lastname())
                .street(orderDto.street())
                .zipcode(orderDto.zipcode())
                .city(orderDto.city())
                .email(orderDto.email())
                .phone(orderDto.phone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems(), shipment))
                .payment(payment)
                .userId(userId)
                .build();
    }

    private static BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getFinalPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(shipment.getPrice());
    }

    public static OrderSummary createOrderSummary(Order newOrder, Payment payment) {
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .payment(payment)
                .build();
    }

    public static OrderRow mapToOrderRow(Long orderId, CartItem cartItem) {
        return OrderRow.builder()
                .quantity(cartItem.getQuantity())
                .productId(cartItem.getProduct().getId())
                .price(cartItem.getProduct().getDiscountPrice() != null ? cartItem.getProduct().getDiscountPrice() : cartItem.getProduct().getPrice())
                .orderId(orderId)
                .build();
    }

    public static OrderRow mapToOrderRowWithQuantity(Long orderId, Shipment shipment) {
        return OrderRow.builder()
                .quantity(1)
                .price((shipment.getPrice()))
                .shipmentId(shipment.getId())
                .orderId(orderId)
                .build();
    }
}
