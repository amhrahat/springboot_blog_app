package com.rahat.blog.mappes.impl;

import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.CreateTagDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.mappes.TagMapper;
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
