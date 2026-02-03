package com.rahat.blog.domain.dtos;

import java.util.UUID;

public record CategoryDto(
        UUID id,
        String name
) {
}
