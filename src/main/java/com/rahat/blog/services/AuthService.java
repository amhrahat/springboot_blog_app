package com.rahat.blog.services;

import com.rahat.blog.domain.dtos.LoginResponseDto;
import com.rahat.blog.domain.dtos.RegResponseDto;
import com.rahat.blog.domain.dtos.RegiRequestDto;

public interface AuthService {
    RegResponseDto registration (String name, String email, String password);
    LoginResponseDto login(String email, String password);
}
