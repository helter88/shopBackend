package com.artur.shop.product.service;

import com.artur.shop.category.repository.CategoryRepository;
import com.artur.shop.common.model.Category;
import com.artur.shop.common.model.Product;
import com.artur.shop.common.repository.ProdutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProdutRepository produtRepository;
    private final CategoryRepository categoryRepository;
    public Page<Product> getProducts(Pageable pageable){
        return produtRepository.findAll(pageable);
    }

    public Product getProduct(String slug) {
        return produtRepository.findBySlug(slug).orElseThrow();
    }

    public List<String> searchPrompt(String query) {
        List<String> results = new ArrayList<>();
        Pageable limit = PageRequest.of(0, 5);

        // Szukaj według nazwy produktu
        List<Product> productsByName = produtRepository.findByNameContaining(query, limit);
        for (Product product : productsByName) {
            if (results.size() < 5) {
                results.add(product.getName());
            }
        }

        // Szukaj według opisu
        if (results.size() < 5) {
            List<Product> productsByDescription = produtRepository.findByDescriptionContaining(query, limit);
            for (Product product : productsByDescription) {
                if (results.size() < 5 && !results.contains(product.getDescription())) {
                    results.add(product.getDescription());
                }
            }
        }

        // Szukaj według kategorii
        if (results.size() < 5) {
            Optional<Category> category = categoryRepository.findByName(query);
            if (category.isPresent()){
                List<Product> productsByCategory = produtRepository.findByCategoryId(category.get().getId());
                for (Product product : productsByCategory) {
                    if (results.size() < 5 && !results.contains(category.get().getName())) {
                        results.add(category.get().getName());
                    }
                }
            }

        }

        return results;
    }

    public Page<Product> searchProducts(String query, Pageable pageable){
        return produtRepository.searchByQuery(query, pageable);
    }
}
