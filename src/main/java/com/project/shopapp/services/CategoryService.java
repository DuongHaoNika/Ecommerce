package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    @Override
    public List<Category> getllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long id, CategoryDTO category) {
        Category oldCategory = getCategory(id);
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return oldCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
