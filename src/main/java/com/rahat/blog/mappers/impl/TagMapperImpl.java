package com.rahat.blog.mappers.impl;

import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.CreateTagDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.mappers.TagMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl implements TagMapper {


    @Override
    public CreateTagCommand fromDto(CreateTagDto createTagDto) {
        return new CreateTagCommand(
                createTagDto.name()
        );
    }

    @Override
    public TagDto toDto(Tag tag) {
        return  new TagDto(
                tag.getId(),
                tag.getName()
        );
    }
}
