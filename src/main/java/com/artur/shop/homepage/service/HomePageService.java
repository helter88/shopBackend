package com.artur.shop.homepage.service;

import com.artur.shop.admin.product.model.ProductImage;
import com.artur.shop.common.dto.ProductListDto;
import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.ProdutRepository;
import com.artur.shop.homepage.controller.dto.HomePageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {
    private final ProdutRepository produtRepository;

    public HomePageDto getDiscountedProducts() {
        List<Product> products = produtRepository.findTop10ByDiscountPriceNotNull();
        List<ProductListDto> productListDto = products.stream().map(prod -> {
             return  new ProductListDto(
                    prod.getId(),
                    prod.getName(),
                    prod.getDescription(),
                    prod.getPrice(),
                    prod.getCurrency(),
                    prod.getSlug(),
                    prod.getDiscountPrice(),
                    prod.getProductImages().stream().findFirst().orElse(null)
            );
        }).toList();
        return new HomePageDto(productListDto);
    };
}
