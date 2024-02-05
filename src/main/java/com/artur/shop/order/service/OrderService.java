package com.artur.shop.order.service;

import com.artur.shop.common.model.Cart;
import com.artur.shop.common.model.CartItem;
import com.artur.shop.common.repository.CartItemRepository;
import com.artur.shop.common.repository.CartRepository;
import com.artur.shop.order.model.Order;
import com.artur.shop.order.model.OrderDto;
import com.artur.shop.order.model.OrderRow;
import com.artur.shop.order.model.OrderStatus;
import com.artur.shop.order.model.OrderSummary;
import com.artur.shop.order.model.Shipment;
import com.artur.shop.order.repository.OrderRowRepository;
import com.artur.shop.order.repository.OrderRepository;
import com.artur.shop.order.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;
    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        Cart cart = cartRepository.findById(orderDto.cartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.shipmentId()).orElseThrow();
        Order order = Order.builder()
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
                .build();
        Order newOrder = orderRepository.save(order);
        saveOrderRows(cart, newOrder.getId(), shipment);
        cartItemRepository.deleteByCartId(orderDto.cartId());
        cartRepository.deleteCartById(orderDto.cartId());
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .build();
    }

    private BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(shipment.getPrice());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        cart.getItems().stream()
                .map(cartItem -> OrderRow.builder()
                        .quantity(cartItem.getQuantity())
                        .productId(cartItem.getProduct().getId())
                        .price(cartItem.getProduct().getPrice())
                        .orderId(orderId)
                        .build()
                )
                .peek(orderRowRepository::save)
                .toList();

        orderRowRepository.save(OrderRow.builder()
                        .quantity(1)
                        .price((shipment.getPrice()))
                        .shipmentId(shipment.getId())
                        .orderId(orderId)
                .build());
    }
}
