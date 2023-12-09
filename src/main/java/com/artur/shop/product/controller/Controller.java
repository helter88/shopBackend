package com.artur.shop.product.controller;

import com.artur.shop.product.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("/products")
    public List<Product> getProducts(){
        return List.of(
                new Product("Prod1", "Cat1", "Description 1", new BigDecimal("19.99"), "PLN"),
                new Product("Prod2", "Cat2", "Description 2", new BigDecimal("389.00"), "PLN"),
                new Product("Prod3", "Cat3", "Description 3", new BigDecimal("12.95"), "PLN"),
                new Product("Prod4", "Cat4", "Description 4", new BigDecimal("33.10"), "PLN")
                );
    }
}
