package com.project.shopapp.controller;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<String> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Create new category successfully");
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories = categoryService.getllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable("id") long id,
            @RequestBody CategoryDTO categoryDTO){
        Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category successfully");
    }
}
