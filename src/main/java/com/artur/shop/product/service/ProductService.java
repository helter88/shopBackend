package com.artur.shop.product.service;

import com.artur.shop.product.model.Product;
import com.artur.shop.product.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProdutRepository produtRepository;
    public Page<Product> getProducts(Pageable pageable){
        return produtRepository.findAll(pageable);
    }

    public Product getProduct(String slug) {
        return produtRepository.findBySlug(slug).orElseThrow();
    }
}
