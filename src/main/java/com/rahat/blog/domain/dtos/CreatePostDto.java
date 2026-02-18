package com.rahat.blog.domain.dtos;

import com.rahat.blog.domain.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

public record CreatePostDto(

        @NotBlank(message = "Title must be provided")
        @Length(max = 255, message = "Title must be between 1 and 255 characters")
        String title,

        @NotBlank(message = "Content must be provided")
        String content,

        @NotNull(message = "Status must be provided")
        PostStatus status,

        @NotNull(message = "Category ID must be provided")
        UUID categoryId,

        Set<UUID> tagIds
) {
}
