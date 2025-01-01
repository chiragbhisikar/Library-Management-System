package com.chiragbhisikar.Library.Management.System.Controller;


import com.chiragbhisikar.Library.Management.System.DTO.CategoryDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Category;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCategory() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDto> categoryDto = categoryService.convertCategoriesToDto(categories);

            return ResponseEntity.ok(new ApiResponse("Success", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            CategoryDto categoryDto = categoryService.convertCategoryToDto(category);

            return ResponseEntity.ok(new ApiResponse("Success", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam(name = "name") String name) {
        try {
            System.out.println(name);
            Category category = categoryService.getCategoryByName(name);
            CategoryDto categoryDto = categoryService.convertCategoryToDto(category);

            return ResponseEntity.ok(new ApiResponse("Success", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category newCategory = categoryService.addCategory(category);
            CategoryDto categoryDto = categoryService.convertCategoryToDto(newCategory);

            return ResponseEntity.ok(new ApiResponse("Category Added Successfully !", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        try {
            Category newCategory = categoryService.updateCategory(category, id);
            CategoryDto categoryDto = categoryService.convertCategoryToDto(newCategory);

            return ResponseEntity.ok(new ApiResponse("Category Updated Successfully !", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Category Deleted Successfully !", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
