package com.artur.shop.common.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String currency;
    private String slug;
    @OneToMany
    @JoinColumn(name = "productId")
    private List<Review> reviews;

    public BigDecimal getFinalPrice() {
        return discountPrice != null ? discountPrice : price;
    }
}