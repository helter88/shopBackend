package com.artur.shop.admin.product.controller;

import com.artur.shop.admin.product.controller.dto.AdminProductDto;
import com.artur.shop.admin.product.controller.dto.UploadImageDto;
import com.artur.shop.admin.product.model.AdminProduct;
import com.artur.shop.admin.product.service.AdminProductImageService;
import com.artur.shop.admin.product.service.AdminProductService;
import com.github.slugify.Slugify;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.artur.shop.admin.common.utils.SlugifyUtils.slugging;

@RestController
@RequiredArgsConstructor
public class AdminProductController {

    public static final Long EMPTY_ID = null;
    private final AdminProductService adminProductService;
    private final AdminProductImageService adminProductImageService;
    @GetMapping("/admin/products")
    public Page<AdminProduct> getProducts(Pageable pageable) {
        return adminProductService.getProducts(pageable);
    }
    @GetMapping("/admin/products/{id}")
    public AdminProduct getProduct(@PathVariable Long id) {
        return adminProductService.getProduct(id);
    }

    @PostMapping("/admin/products")
    public AdminProduct createProduct(@RequestBody @Valid AdminProductDto adminProductDto) {
        return adminProductService.createProduct(mapAdminProduct(adminProductDto, EMPTY_ID)
        );
    }

    @PutMapping("/admin/products/{id}")
    public AdminProduct updateProduct(@RequestBody @Valid AdminProductDto adminProductDto, @PathVariable Long id) {
        return adminProductService.updateProduct(mapAdminProduct(adminProductDto, id)
        );
    }

    @DeleteMapping("/admin/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
    }

    @PostMapping("/admin/products/uploadimage")
    public UploadImageDto uploadImage(@RequestParam("file") MultipartFile multipartFile){
        try (InputStream inputStream = multipartFile.getInputStream()){
            String saveFileName = adminProductImageService.uploadImage(multipartFile.getOriginalFilename(), inputStream);
            return new UploadImageDto(saveFileName);
        } catch (IOException e) {
            throw new RuntimeException("Problem with uploading file", e);
        }
    }
    @GetMapping("/data/productImages/{fileName}")
    public ResponseEntity<Resource> serveFiles(@PathVariable String fileName) throws IOException {
        Resource file = adminProductImageService.serveFiles(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(fileName)))
                .body(file);
    }



    private AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.name())
                .description(adminProductDto.description())
                .categoryId(adminProductDto.categoryId())
                .price(adminProductDto.price())
                .currency(adminProductDto.currency())
                .image(adminProductDto.image())
                .slug(slugging(adminProductDto.slug()))
                .build();
    }
}
