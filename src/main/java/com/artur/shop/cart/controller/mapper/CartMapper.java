package com.artur.shop.cart.controller.mapper;

import com.artur.shop.cart.controller.dto.CartSummaryDto;
import com.artur.shop.cart.controller.dto.CartSummaryItemDto;
import com.artur.shop.cart.controller.dto.ProductDto;
import com.artur.shop.cart.controller.dto.SummaryDto;
import com.artur.shop.cart.model.Cart;
import com.artur.shop.cart.model.CartItem;
import com.artur.shop.common.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {
    public static CartSummaryDto mapToCartSummary(Cart cart) {
        return new CartSummaryDto(cart.getId(), mapCartItems(cart.getItems()), mapToSummary(cart.getItems()));
    }


    private static List<CartSummaryItemDto> mapCartItems(List<CartItem> items) {
        return items.stream()
                .map(cartItem -> mapToCartItem(cartItem))
                .toList();
    }

    private static CartSummaryItemDto mapToCartItem(CartItem cartItem) {
        return new CartSummaryItemDto(cartItem.getId(), cartItem.getQuantity(), mapToProductDto(cartItem.getProduct()), calculateLineValue(cartItem));
    }

    private static BigDecimal calculateLineValue(CartItem cartItem) {
        return cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }

    private static ProductDto mapToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getCurrency(), product.getImage(), product.getSlug());
    }

    private static SummaryDto mapToSummary(List<CartItem> items) {
        return new SummaryDto(sumValues(items));
    }

    private static BigDecimal sumValues(List<CartItem> items) {
        return items.stream()
                .map(CartMapper::calculateLineValue)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
