package com.artur.shop.cart.service;

import com.artur.shop.cart.model.Cart;
import com.artur.shop.cart.model.CartProductDto;
import com.artur.shop.cart.repository.CartRepository;
import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.ProdutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProdutRepository produtRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    void shouldAddProductToCartWhenCartIdNotExists() {
        //given
        Long cartId = 0L;
        CartProductDto cartProductDto = new CartProductDto(1l, 1);
        when(produtRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).build()));
        when(cartRepository.save(any())).thenReturn(Cart.builder().id(1L).build());
        //when
        Cart result = cartService.addProductToCart(cartId, cartProductDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void shouldAddProductToCartWhenCartIdExists() {
        //given
        Long cartId = 1L;
        CartProductDto cartProductDto = new CartProductDto(1l, 1);
        when(produtRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).build()));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(Cart.builder().id(1L).build()));
        //when
        Cart result = cartService.addProductToCart(cartId, cartProductDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

}