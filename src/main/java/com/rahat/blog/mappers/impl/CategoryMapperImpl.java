package com.rahat.blog.mappers.impl;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.CreateCategoryDto;
import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.mappers.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CreateCategoryCommand fromDto(CreateCategoryDto createCategoryDto) {
        return new CreateCategoryCommand(
                createCategoryDto.name()
        );
    }

    @Override
    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }
}
