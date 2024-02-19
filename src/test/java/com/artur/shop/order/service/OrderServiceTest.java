package com.artur.shop.order.service;

import com.artur.shop.common.mail.EmailClientService;
import com.artur.shop.common.mail.FakeEmailService;
import com.artur.shop.common.model.Cart;
import com.artur.shop.common.model.CartItem;
import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.CartItemRepository;
import com.artur.shop.common.repository.CartRepository;
import com.artur.shop.order.model.OrderDto;
import com.artur.shop.common.model.OrderStatus;
import com.artur.shop.order.model.OrderSummary;
import com.artur.shop.order.model.Payment;
import com.artur.shop.order.model.PaymentType;
import com.artur.shop.order.model.Shipment;
import com.artur.shop.order.repository.OrderRepository;
import com.artur.shop.order.repository.OrderRowRepository;
import com.artur.shop.order.repository.PaymentRepository;
import com.artur.shop.order.repository.ShipmentRepository;
import com.artur.shop.security.model.User;
import com.artur.shop.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ShipmentRepository shipmentRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRowRepository orderRowRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private EmailClientService emailClientService;
    @InjectMocks
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldPlaceOrder() {
        //given

        OrderDto orderDto = new OrderDto(
                "firsrName",
                "lastName",
                "street",
                "zipcode",
                "city",
                "email",
                "phone",
                1L,
                2L,
                3L);
        when(cartRepository.findById(any())).thenReturn(createCart());
        when(shipmentRepository.findById(any())).thenReturn(createShipment());
        when(paymentRepository.findById(any())).thenReturn(createPayment());
        when(orderRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        when(emailClientService.getInstance()).thenReturn(new FakeEmailService());
        when(userRepository.findByUsername(anyString())).thenReturn(createUser());
        String userName = "test@test";
        //when
        OrderSummary orderSummary = orderService.placeOrder(orderDto, userName);
        //then
        assertThat(orderSummary).isNotNull();
        assertThat(orderSummary.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(orderSummary.getGrossValue()).isEqualTo(new BigDecimal("36.22"));
        assertThat(orderSummary.getPayment().getType()).isEqualTo(PaymentType.BANK_TRANSFER);
        assertThat(orderSummary.getPayment().getName()).isEqualTo("test payment");
        assertThat(orderSummary.getPayment().getId()).isEqualTo(1L);
    }

    private Optional<Payment> createPayment() {
        return Optional.of(
                Payment.builder()
                        .id(1L)
                        .name("test payment")
                        .type(PaymentType.BANK_TRANSFER)
                        .defaultPayment(true)
                        .build()
        );
    }

    private Optional<Shipment> createShipment() {
        return Optional.of(
                Shipment.builder()
                        .id(2L)
                        .price(new BigDecimal("14.00"))
                        .build()
        );
    }

    private Optional<Cart> createCart() {
        return Optional.of(Cart.builder()
                        .id(1L)
                        .created(LocalDateTime.now())
                        .items(createItems())
                .build());
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .password("test")
                .username("test@test")
                .enabled(true)
                .build();
    }

    private List<CartItem> createItems() {
        return List.of(
                CartItem.builder()
                        .id(1L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(1L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build(),
                CartItem.builder()
                        .id(2L)
                        .cartId(2L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(2L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build()
        );
    }
}