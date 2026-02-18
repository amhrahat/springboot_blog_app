package com.rahat.blog.domain.dtos;

import com.rahat.blog.domain.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record PostDto(
        UUID id,
        String title,
        String content,
        PostStatus status,
        UUID authorId,
        String authorName,
        CategoryDto category,
        Set<TagDto> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
