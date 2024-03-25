package com.artur.shop.order.service;

import com.artur.shop.common.mail.EmailClientService;
import com.artur.shop.common.model.Cart;
import com.artur.shop.common.model.UserData;
import com.artur.shop.common.repository.CartItemRepository;
import com.artur.shop.common.repository.CartRepository;
import com.artur.shop.common.repository.UserDataRepository;
import com.artur.shop.order.model.Order;
import com.artur.shop.order.model.OrderDto;
import com.artur.shop.order.model.OrderListDto;
import com.artur.shop.order.model.OrderSummary;
import com.artur.shop.order.model.Payment;
import com.artur.shop.order.model.Shipment;
import com.artur.shop.order.repository.OrderRowRepository;
import com.artur.shop.order.repository.OrderRepository;
import com.artur.shop.order.repository.PaymentRepository;
import com.artur.shop.order.repository.ShipmentRepository;
import com.artur.shop.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.artur.shop.order.service.mapper.OrderDtoMapper.mapToOrderListDto;
import static com.artur.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static com.artur.shop.order.service.mapper.OrderMapper.createNewOrder;
import static com.artur.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static com.artur.shop.order.service.mapper.OrderMapper.mapOrderDtoToUserData;
import static com.artur.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static com.artur.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, String userName) {
        Cart cart = cartRepository.findById(orderDto.cartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.shipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.paymentId()).orElseThrow();
        Long userId =userRepository.findByUsername(userName).getId();
        Order newOrder = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment, userId));
        Long userDataId  = userDataRepository.findByUserId(userId) == null ? null : userDataRepository.findByUserId(userId).getId();
        userDataRepository.save(mapOrderDtoToUserData(orderDto, userId, userDataId));
        saveOrderRows(cart, newOrder.getId(), shipment, payment);
        clearOrderCart(orderDto);
        sendConfirmEmail(newOrder);
        return createOrderSummary(newOrder, payment);
    }

    private void sendConfirmEmail(Order newOrder) {
        emailClientService.getInstance()
                .send(newOrder.getEmail(),"Your order is accepted", createEmailMessage(newOrder));
    }

    private void clearOrderCart(OrderDto orderDto) {
        cartItemRepository.deleteByCartId(orderDto.cartId());
        cartRepository.deleteCartById(orderDto.cartId());
    }


    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment, Payment payment) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRow(orderId, cartItem)
                )
                .peek(orderRowRepository::save)
                .toList();

        orderRowRepository.save(mapToOrderRowWithQuantity(orderId, shipment));
    }

    public List<OrderListDto> getOrdersByUserName(String name) {
        Long userId = userRepository.findByUsername(name).getId();
        return mapToOrderListDto(orderRepository.findByUserId(userId));
    }
}
