package com.rahat.blog.services;


import com.rahat.blog.domain.commands.CreateTagCommand;
import com.rahat.blog.domain.dtos.TagDto;

import java.util.List;
import java.util.UUID;

public interface TagService {
    TagDto createTag(CreateTagCommand createTagCommand);
    List<TagDto> tagLists();
    void deleteTag(UUID tagId);
}
