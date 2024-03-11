package com.artur.shop.admin.product.controller;

import com.artur.shop.admin.product.controller.dto.AdminProductDto;
import com.artur.shop.admin.product.controller.dto.AdminProductWithImagesDto;
import com.artur.shop.admin.product.model.AdminProduct;
import com.artur.shop.admin.product.service.AdminProductImageService;
import com.artur.shop.admin.product.service.AdminProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.artur.shop.admin.common.utils.SlugifyUtils.slugging;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    public static final Long EMPTY_ID = null;
    private final AdminProductService adminProductService;
    private final AdminProductImageService adminProductImageService;
    private final ObjectMapper objectMapper;
    @GetMapping
    public Page<AdminProduct> getProducts(Pageable pageable) {
        return adminProductService.getProducts(pageable);
    }
    @GetMapping("{id}")
    public AdminProductWithImagesDto getProduct(@PathVariable Long id) {
        return adminProductService.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestParam("productData") String productData,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {

        try {
            AdminProductDto productDTO = objectMapper.readValue(productData, AdminProductDto.class);
            adminProductService.addProduct(productDTO, images);
            return ResponseEntity.ok().body("{\"message\":\"Product added successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Invalid product data\"}");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?>  updateProduct(  @RequestParam("productData") String productData,
                                              @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                              @PathVariable Long id) {
      try {
          AdminProductDto productDTO = objectMapper.readValue(productData, AdminProductDto.class);
          adminProductService.updateProduct(productDTO, images, id);
          return ResponseEntity.ok().body("{\"message\":\"Product modified successfully\"}");
      } catch (Exception e) {
          return ResponseEntity.badRequest().body("{\"error\":\"Invalid product data\"}");
      }
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
    }

    private AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.name())
                .description(adminProductDto.description())
                .categoryId(adminProductDto.categoryId())
                .price(adminProductDto.price())
                .currency(adminProductDto.currency())
                .slug(slugging(adminProductDto.slug()))
                .discountPrice(adminProductDto.discountPrice())
                .build();
    }
}
