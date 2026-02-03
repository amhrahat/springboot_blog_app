package com.rahat.blog.services.impl;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CreateCategoryCommand createCategoryCommand) {
        Category category = new Category(
                null,
                createCategoryCommand.name()
        );
        Category saved = categoryRepository.save(category);
        return new CategoryDto(saved.getId(), saved.getName());
    }
}

