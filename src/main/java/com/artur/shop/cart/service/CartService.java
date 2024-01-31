package com.artur.shop.cart.service;

import com.artur.shop.cart.model.Cart;
import com.artur.shop.cart.model.CartItem;
import com.artur.shop.cart.model.CartProductDto;
import com.artur.shop.cart.repository.CartRepository;
import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProdutRepository produtRepository;
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }
    @Transactional
    public Cart addProductToCart(Long id, CartProductDto cartProductDto) {
        Cart initializedCart = getInitializedCart(id);
        initializedCart.addProduct(CartItem.builder()
                        .quantity(cartProductDto.quantity())
                        .product(getProduct(cartProductDto.productId()))
                        .cartId(initializedCart.getId())
                .build());
        return initializedCart;
    }

    private Product getProduct(Long id) {
        return produtRepository.findById(id).orElseThrow();
    }

    private Cart getInitializedCart(Long id) {
        if (id == null || id <= 0){
            return cartRepository.save(Cart.builder().created(LocalDateTime.now()).build());
        }
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Cart updateCart(Long id, List<CartProductDto> cartProductDtos) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cart.getItems().forEach(cartItem -> {
            cartProductDtos.stream()
                    .filter(cartProductDto -> cartItem.getProduct().getId().equals((cartProductDto.productId())))
                    .findFirst()
                    .ifPresent(cartProductDto -> cartItem.setQuantity(cartProductDto.quantity()));
        });
        return cart;
    }
}
