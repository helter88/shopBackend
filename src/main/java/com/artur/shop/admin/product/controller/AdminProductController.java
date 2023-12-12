package com.artur.shop.admin.product.controller;

import com.artur.shop.admin.product.model.AdminProduct;
import com.artur.shop.admin.product.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;
    @GetMapping("/admin/products")
    public Page<AdminProduct> getProduct(Pageable pageable) {
        return adminProductService.getProducts(pageable);
    }
}
