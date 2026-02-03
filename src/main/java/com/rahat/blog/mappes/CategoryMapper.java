package com.rahat.blog.mappes;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.CreateCategoryDto;
import com.rahat.blog.domain.entities.Category;
import org.springframework.stereotype.Component;

public interface CategoryMapper {

    CreateCategoryCommand fromDto(CreateCategoryDto createCategoryDto);
    CategoryDto toDto(Category category);
}
