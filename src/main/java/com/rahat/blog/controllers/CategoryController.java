package com.rahat.blog.controllers;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.CreateCategoryDto;
import com.rahat.blog.mappers.CategoryMapper;
import com.rahat.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    CategoryController(CategoryService categoryService, CategoryMapper categoryMapper){
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryDto createCategoryDto
            ){
        CreateCategoryCommand command =  categoryMapper.fromDto(createCategoryDto);
        CategoryDto categoryDto = categoryService.createCategory(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryDto);

    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> viewCategory(){
        List<CategoryDto> dtos = categoryService.categoryLists();
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
