package com.rahat.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegiRequestDto(

        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
