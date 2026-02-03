package com.rahat.blog.services;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryCommand createCategoryCommand);
    List<CategoryDto> categoryLists();
    void deleteCategory(UUID categoryId);
}
