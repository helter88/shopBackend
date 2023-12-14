package com.artur.shop.admin.product.controller;

import com.artur.shop.admin.product.controller.dto.AdminProductDto;
import com.artur.shop.admin.product.model.AdminProduct;
import com.artur.shop.admin.product.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminProductController {

    public static final Long EMPTY_ID = null;
    private final AdminProductService adminProductService;
    @GetMapping("/admin/products")
    public Page<AdminProduct> getProducts(Pageable pageable) {
        return adminProductService.getProducts(pageable);
    }
    @GetMapping("/admin/products/{id}")
    public AdminProduct getProduct(@PathVariable Long id) {
        return adminProductService.getProduct(id);
    }

    @PostMapping("/admin/products")
    public AdminProduct createProduct(@RequestBody AdminProductDto adminProductDto) {
        return adminProductService.createProduct(mapAdminProduct(adminProductDto, EMPTY_ID)
        );
    }

    @PutMapping("/admin/products/{id}")
    public AdminProduct updateProduct(@RequestBody AdminProductDto adminProductDto, @PathVariable Long id) {
        return adminProductService.updateProduct(mapAdminProduct(adminProductDto, id)
        );
    }

    private static AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.name())
                .description(adminProductDto.description())
                .category(adminProductDto.category())
                .price(adminProductDto.price())
                .currency(adminProductDto.currency())
                .build();
    }

}
