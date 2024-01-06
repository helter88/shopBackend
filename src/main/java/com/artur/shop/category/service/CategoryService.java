package com.artur.shop.category.service;

import com.artur.shop.category.model.Category;
import com.artur.shop.category.model.CategoryProductsDto;
import com.artur.shop.category.repository.CategoryRepository;
import com.artur.shop.product.model.Product;
import com.artur.shop.product.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        return new CategoryProductsDto(category, page);
    }
}
