package com.rahat.blog.domain.dtos;

public record LoginResponseDto(
        String token,
        String tokenType,
        Long expiresIn,
        String email
) {
}