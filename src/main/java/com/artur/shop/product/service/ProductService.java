package com.artur.shop.product.service;

import com.artur.shop.product.model.Product;
import com.artur.shop.product.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProdutRepository produtRepository;
    public List<Product> getProducts(){
        return produtRepository.findAll();
    }
}
