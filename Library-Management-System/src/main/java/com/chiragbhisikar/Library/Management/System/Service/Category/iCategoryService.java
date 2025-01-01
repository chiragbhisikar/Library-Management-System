package com.chiragbhisikar.Library.Management.System.Service.Category;

import com.chiragbhisikar.Library.Management.System.Model.Category;

import java.util.List;

public interface iCategoryService {
    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategory(Long id);
}
