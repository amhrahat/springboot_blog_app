package com.rahat.blog.mappers.impl;

import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.PostDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.Post;
import com.rahat.blog.mappers.PostMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDto toDto(Post post) {
        // Null-safe handling for category
        CategoryDto categoryDto = null;
        if (post.getCategory() != null) {
            categoryDto = new CategoryDto(
                    post.getCategory().getId(),
                    post.getCategory().getName()
            );
        }


        Set<TagDto> tagDtos = new HashSet<>();
        if (post.getTags() != null) {
            tagDtos = post.getTags().stream()
                    .map(tag -> new TagDto(tag.getId(), tag.getName()))
                    .collect(Collectors.toSet());
        }


        UUID authorId = null;
        String authorUsername = null;
        if (post.getAuthor() != null) {
            authorId = post.getAuthor().getId();
            authorUsername = post.getAuthor().getName();
        }

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus(),
                authorId,
                authorUsername,
                categoryDto,
                tagDtos,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}