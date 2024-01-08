package com.artur.shop.product.controller;

import com.artur.shop.product.model.Product;
import com.artur.shop.product.model.ProductListDto;
import com.artur.shop.product.service.ProductService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Page<ProductListDto> getProducts(Pageable pageable){
        Page<Product> products = productService.getProducts(pageable);
        List<ProductListDto> productListDto = products.getContent().stream()
                .map(product -> new ProductListDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCurrency(), product.getImage(), product.getSlug()))
                .toList();
        return new PageImpl<>(productListDto, pageable, products.getTotalElements());

    }

    @GetMapping("/products/{slug}")
    public Product getProduct(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+") @Length(max = 255) String slug){
        return productService.getProduct(slug);
    }
}
