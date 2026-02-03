package com.rahat.blog.mappes.impl;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CreateCategoryDto;
import com.rahat.blog.mappes.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CreateCategoryCommand fromDto(CreateCategoryDto createCategoryDto) {
        return new CreateCategoryCommand(
                createCategoryDto.name()
        );
    }
}
