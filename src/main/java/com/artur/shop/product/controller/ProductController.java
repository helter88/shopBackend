package com.artur.shop.product.controller;

import com.artur.shop.common.dto.ProductListDto;
import com.artur.shop.common.model.Product;
import com.artur.shop.product.service.ProductService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductListDto> getProducts(Pageable pageable){
        Page<Product> products = productService.getProducts(pageable);
        List<ProductListDto> productListDto = products.getContent().stream()
                .map(product -> new ProductListDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCurrency(),
                        product.getSlug(),
                        product.getDiscountPrice(),
                        product.getProductImages().stream().findFirst().orElse(null)
                ))
                .toList();
        return new PageImpl<>(productListDto, pageable, products.getTotalElements());

    }

    @GetMapping("/{slug}")
    public Product getProduct(@PathVariable @Length(max = 255) String slug){
        return productService.getProduct(slug);
    }

    @GetMapping("/prompt")
    public ResponseEntity<List<String>> searchPrompt(@RequestParam String query) {
        return ResponseEntity.ok(productService.searchPrompt(query));
    }

    @GetMapping("/search")
    public Page<ProductListDto> searchProduct(@RequestParam String query, Pageable pageable ) {
        Page<Product> products = productService.searchProducts(query, pageable);
        List<ProductListDto> productListDto = products.getContent().stream()
                .map(product -> new ProductListDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCurrency(),
                        product.getSlug(),
                        product.getDiscountPrice(),
                        product.getProductImages().stream().findFirst().orElse(null)
                ))
                .toList();
        return  new PageImpl<>(productListDto, pageable, products.getTotalElements());
    }
}
