package com.rahat.blog.mappes;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CreateCategoryDto;
import org.springframework.stereotype.Component;

public interface CategoryMapper {

    CreateCategoryCommand fromDto (CreateCategoryDto createCategoryDto);
}
