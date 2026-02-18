package com.rahat.blog.mappers;

import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.CreateTagDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.Tag;

public interface TagMapper {

    CreateTagCommand fromDto(CreateTagDto createTagDto);
    TagDto toDto(Tag tag);
}
