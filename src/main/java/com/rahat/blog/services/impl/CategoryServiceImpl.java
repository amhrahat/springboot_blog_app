package com.rahat.blog.services.impl;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.domain.entities.User;
import com.rahat.blog.mappers.CategoryMapper;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.services.CategoryService;
import com.rahat.blog.services.CurrentUserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CurrentUserService currentUserService;

    CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CurrentUserService currentUserService){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public CategoryDto createCategory(CreateCategoryCommand createCategoryCommand) {
        User currentUser = currentUserService.getCurrentUser();
        Category category = new Category(
                null,
                createCategoryCommand.name(),
                currentUser
        );
        Category saved = categoryRepository.save(category);
        currentUser.setCategories(List.of(category));
        return categoryMapper.toDto(saved);
    }

    @Override
    public List<CategoryDto> categoryLists() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtos = new ArrayList<>();

        for (Category category : categories){
            CategoryDto dto = categoryMapper.toDto(category);
            dtos.add(dto);
        }
        return dtos;

    }

    @Override
    public void deleteCategory(UUID categoryId) {
        User currentUser = currentUserService.getCurrentUser();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
        if (!category.getAuthor().getId()
                .equals(currentUser.getId())) {


            throw new AccessDeniedException(
                    "You can delete only your own categories"
            );
        }

        categoryRepository.deleteById(categoryId);
    }
}

