package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategory(long id);
    List<Category> getllCategories();
    Category updateCategory(long id, CategoryDTO category);
    void deleteCategory(long id);
}
