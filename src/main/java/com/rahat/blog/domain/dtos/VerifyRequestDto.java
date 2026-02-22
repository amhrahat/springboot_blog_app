package com.rahat.blog.domain.dtos;

import jakarta.validation.constraints.NotNull;

public record VerifyRequestDto(

        @NotNull
        String email,

        @NotNull
        String otp
) {
}
