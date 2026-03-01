package com.rahat.blog.domain.dtos;

import com.rahat.blog.domain.entities.Category;
import com.rahat.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record AiRequestDto(
        String text,
        UUID category,
        Set<UUID> tags


){

}
