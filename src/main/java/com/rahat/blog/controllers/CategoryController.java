package com.rahat.blog.controllers;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.CreateCategoryDto;
import com.rahat.blog.mappes.CategoryMapper;
import com.rahat.blog.services.CategoryService;
import com.rahat.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    CategoryController(CategoryService categoryService, CategoryMapper categoryMapper){
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryDto createCategoryDto
            ){
        CreateCategoryCommand command =  categoryMapper.fromDto(createCategoryDto);
        CategoryDto categoryDto = categoryService.createCategory(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryDto);


    }

    public ResponseEntity<>
}
