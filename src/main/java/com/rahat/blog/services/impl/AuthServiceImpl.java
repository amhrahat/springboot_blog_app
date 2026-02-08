package com.rahat.blog.services.impl;

import com.rahat.blog.domain.dtos.LoginResponseDto;
import com.rahat.blog.domain.dtos.RegResponseDto;
import com.rahat.blog.domain.entities.User;
import com.rahat.blog.repositories.UserRepository;
import com.rahat.blog.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public RegResponseDto registration(String name, String email, String password) {
        if (userRepository.existsByEmail(email)){
            throw new IllegalStateException("Email already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new RegResponseDto(user.getName(), user.getEmail());
    }

    @Override
    public LoginResponseDto login(String email, String password) {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(authentication);

        return new LoginResponseDto(email, "Login successful");
    }
}

