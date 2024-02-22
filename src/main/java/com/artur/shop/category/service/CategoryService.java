package com.artur.shop.category.service;

import com.artur.shop.common.model.Category;
import com.artur.shop.category.dto.CategoryProductsDto;
import com.artur.shop.category.repository.CategoryRepository;
import com.artur.shop.common.dto.ProductListDto;
import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProdutRepository produtRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoryProductsDto getCategoriesWithProducts(String slug, Pageable pageable) {
        Category category = categoryRepository.findBySlug(slug);
        Page<Product> page = produtRepository.findByCategoryId(category.getId(), pageable);
        List<ProductListDto> productListDto = page.getContent().stream()
                .map(product -> new ProductListDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCurrency(), product.getImage(), product.getSlug(), product.getDiscountPrice()))
                .toList();
        return new CategoryProductsDto(category, new PageImpl<>(productListDto, pageable, page.getTotalElements() ));
    }
}
