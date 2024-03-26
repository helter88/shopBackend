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
import com.artur.shop.common.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;
    private final ImageRepository imageRepository;
    private final ProductImageRepository productImageRepository;
    private final CartItemRepository cartItemRepository;

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
        if(images != null) {
            for (MultipartFile file : images) {
                byte[] compressedImage = compressImage(file);
                Image image = Image.builder()
                        .filename(file.getOriginalFilename())
                        .filetype(file.getContentType())
                        .image(compressedImage)
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
    @Transactional
    public void deleteProduct(Long id) {
        if (cartItemRepository.existsByProductId(id)){
            throw new RuntimeException("Can't delete product because of existing element in cart");
        }
        adminProductRepository.deleteProductImagesByProductId(id);
        adminProductRepository.deleteById(id);
        imageRepository.deleteOrphanImages();
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
        if(images != null) {
            for (MultipartFile file : images) {
                byte[] compressedImage = compressImage(file);
                Image image = Image.builder()
                        .filename(file.getOriginalFilename())
                        .filetype(file.getContentType())
                        .image(compressedImage)
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

    private byte[] compressImage(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(800, 800) // można dostosować wymiary
                .outputFormat("jpeg")
                .outputQuality(0.75) // jakość obrazu, 0.75 to 75%
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
    }
}
