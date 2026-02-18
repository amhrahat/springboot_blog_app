package com.rahat.blog.controllers;


import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.CreateTagDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.mappers.TagMapper;
import com.rahat.blog.services.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    TagController(TagService tagService, TagMapper tagMapper){
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(
            @Valid @RequestBody CreateTagDto createTagDto
            ){
        CreateTagCommand command =  tagMapper.fromDto(createTagDto);
        TagDto tagDto = tagService.createTag(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagDto);

    }

    @GetMapping
    public ResponseEntity<List<TagDto>> viewTag(){
        List<TagDto> dtos = tagService.tagLists();
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }

    @DeleteMapping(path = "/{tagId}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable UUID tagId){
        tagService.deleteTag(tagId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
