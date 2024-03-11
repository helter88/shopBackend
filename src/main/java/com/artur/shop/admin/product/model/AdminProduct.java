package com.artur.shop.admin.product.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name= "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    @Enumerated(EnumType.STRING)
    private AdminProductCurrency currency;
    private String slug;
    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductImage> productImages;
}
