package com.rahat.blog.services.impl;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.mappers.CategoryMapper;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto createCategory(CreateCategoryCommand createCategoryCommand) {
        Category category = new Category(
                null,
                createCategoryCommand.name()
        );
        Category saved = categoryRepository.save(category);
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
        categoryRepository.deleteById(categoryId);
    }
}

