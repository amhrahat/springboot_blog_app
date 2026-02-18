package com.rahat.blog.mappers;

import com.rahat.blog.domain.dtos.PostDto;
import com.rahat.blog.domain.entities.Post;

public interface PostMapper {
    PostDto toDto(Post post);
}