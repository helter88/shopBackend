package com.artur.shop.common.model;

import com.artur.shop.admin.product.model.ProductImage;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductImage> productImages;

    public BigDecimal getFinalPrice() {
        return discountPrice != null ? discountPrice : price;
    }
}