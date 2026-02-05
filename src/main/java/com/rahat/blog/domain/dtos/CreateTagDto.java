package com.rahat.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateTagDto(

        @NotBlank(message = "Name must be given")
        @Length(max = 225, message = "Title must be between 1 and 225 characters")
        String name
) {
}
