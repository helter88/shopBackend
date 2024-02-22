package com.artur.shop.homepage.service;

import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {
    private final ProdutRepository produtRepository;

    public List<Product> getDiscountedProducts() {
        return produtRepository.findTop10ByDiscountPriceNotNull();
    }
}
