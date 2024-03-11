package com.artur.shop.admin.product.service;

import com.artur.shop.admin.product.controller.dto.AdminProductDto;
import com.artur.shop.admin.product.controller.dto.AdminProductWithImagesDto;
import com.artur.shop.admin.product.controller.dto.ImageDto;
import com.artur.shop.admin.product.model.AdminProduct;
import com.artur.shop.admin.product.model.Image;
import com.artur.shop.admin.product.model.ProductImage;
import com.artur.shop.admin.product.repository.AdminProductRepository;
import com.artur.shop.admin.product.repository.ImageRepository;
import com.artur.shop.admin.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;
    private final ImageRepository imageRepository;
    private final ProductImageRepository productImageRepository;

    public Page<AdminProduct> getProducts(Pageable pageable) {
        return adminProductRepository.findAllWithImages(pageable);
    }

    public AdminProductWithImagesDto getProduct(Long id) {
        AdminProduct product = adminProductRepository.findByIdWithImages(id);
        List<ImageDto> imageDtos = product.getProductImages().stream()
                .map(productImage -> {
                    Image image = productImage.getImage();
                    String base64 = Base64.getEncoder().encodeToString(image.getImage());
                    return new ImageDto(
                            image.getId(),
                            image.getFilename(),
                            image.getFiletype(),
                            base64
                    );
                })
                .toList();

        return new AdminProductWithImagesDto(
                product.getName(),
                product.getCategoryId(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getSlug(),
                product.getDiscountPrice(),
                imageDtos);
    }

    public AdminProduct createProduct(AdminProduct adminProduct) {
        return adminProductRepository.save(adminProduct);
    }

    @Transactional
    public void updateProduct(AdminProductDto productDTO, List<MultipartFile> images, Long id) throws IOException {

        AdminProduct product = adminProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDTO.name());
        product.setCategoryId(productDTO.categoryId());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());
        product.setDiscountPrice(productDTO.discountPrice());
        product.setCurrency(productDTO.currency());
        product.setSlug(productDTO.slug());

        adminProductRepository.save(product);

        adminProductRepository.deleteProductImagesByProductId(product.getId());
        imageRepository.deleteOrphanImages();

        for (MultipartFile file : images) {
            Image image = Image.builder()
                    .filename(file.getOriginalFilename())
                    .filetype(file.getContentType())
                    .image(file.getBytes())
                    .build();
            image = imageRepository.save(image);

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .image(image)
                    .build();

            productImageRepository.save(productImage);
        }
    }

    public void deleteProduct(Long id) {
        adminProductRepository.deleteById(id);
    }

    public void addProduct(AdminProductDto productDTO, List<MultipartFile> images) throws IOException {

        AdminProduct product = AdminProduct.builder()
                .name(productDTO.name())
                .categoryId(productDTO.categoryId())
                .description(productDTO.description())
                .price(productDTO.price())
                .discountPrice(productDTO.discountPrice())
                .currency(productDTO.currency())
                .slug(productDTO.slug())
                .build();

        product = adminProductRepository.save(product);

        for (MultipartFile file : images) {
            Image image = Image.builder()
                    .filename(file.getOriginalFilename())
                    .filetype(file.getContentType())
                    .image(file.getBytes())
                    .build();
            image = imageRepository.save(image);

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .image(image)
                    .build();

            productImageRepository.save(productImage);
        }
    }
}
