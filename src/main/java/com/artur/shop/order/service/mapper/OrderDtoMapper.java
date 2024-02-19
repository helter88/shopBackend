package com.artur.shop.order.service.mapper;

import com.artur.shop.order.model.Order;
import com.artur.shop.order.model.OrderListDto;

import java.util.List;

public class OrderDtoMapper {
    public static List<OrderListDto> mapToOrderListDto(List<Order> orders) {
        return orders.stream().map(order -> new OrderListDto(order.getId(), order.getPlaceDate(), order.getOrderStatus(), order.getGrossValue())).toList();
    }
}
