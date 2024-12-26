package com.chiragbhisikar.Library.Management.System.Service.Category;

import com.chiragbhisikar.Library.Management.System.DTO.CategoryDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Category;
import com.chiragbhisikar.Library.Management.System.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements iCategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new NotFoundException("Category not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new NotFoundException("Category not found!");
                });
    }

    public CategoryDto convertCategoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }


    public List<CategoryDto> convertCategoriesToDto(List<Category> categories) {
        return categories.stream().map(this::convertCategoryToDto).collect(Collectors.toList());
    }
}