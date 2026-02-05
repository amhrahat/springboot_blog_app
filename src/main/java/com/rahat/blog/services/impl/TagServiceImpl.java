package com.rahat.blog.services.impl;

import com.rahat.blog.domain.commands.CreateCategoryCommand;
import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.mappes.CategoryMapper;
import com.rahat.blog.mappes.TagMapper;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.repositories.TagRepository;
import com.rahat.blog.services.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper){
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }


    @Override
    public TagDto createTag(CreateTagCommand createTagCommand) {
        Tag tag = new Tag(
                null,
                createTagCommand.name()
        );
        Tag saved = tagRepository.save(tag);
        return tagMapper.toDto(saved);
    }



    @Override
    public List<TagDto> tagLists() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> dtos = new ArrayList<>();

        for (Tag tag : tags){
          TagDto dto = tagMapper.toDto(tag);
                        dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void deleteTag(UUID tagId) {
        tagRepository.deleteById(tagId);
    }

}

