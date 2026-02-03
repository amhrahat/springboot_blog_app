package com.rahat.blog.services;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryCommand createCategoryCommand);
}
