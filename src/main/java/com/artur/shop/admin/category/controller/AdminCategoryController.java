package com.artur.shop.admin.category.controller;

import com.artur.shop.admin.category.controller.dto.AdminCategoryDto;
import com.artur.shop.admin.category.model.AdminCategory;
import com.artur.shop.admin.category.service.AdminCategoryService;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;
    @GetMapping
    public List<AdminCategory> getCategories() {
        return adminCategoryService.getCategories();
    }

    @GetMapping("/{id}")
    public AdminCategory getCategory(@PathVariable Long id) {
        return adminCategoryService.getCategory(id);
    }

    @PostMapping
    public AdminCategory saveCategory(@RequestBody AdminCategoryDto adminCategoryDto) {
        return adminCategoryService.saveCategory(mapToAdminCategory(adminCategoryDto, null));
    }


    @PutMapping("/{id}")
    public AdminCategory updateCategory(@RequestBody AdminCategoryDto adminCategoryDto, @PathVariable Long id) {
        return adminCategoryService.updateCategory(mapToAdminCategory(adminCategoryDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
    }
    private AdminCategory mapToAdminCategory(AdminCategoryDto adminCategoryDto, Long id) {
        return AdminCategory.builder()
                .id(id)
                .name(adminCategoryDto.name())
                .description(adminCategoryDto.description())
                .slug(slugifyCategoryName(adminCategoryDto.slug()))
                .build();
    }

    private String slugifyCategoryName(String slug) {
        Slugify slugify = new Slugify();
        return  slugify.withCustomReplacement("_", "-")
                .slugify(slug);
    }
}
