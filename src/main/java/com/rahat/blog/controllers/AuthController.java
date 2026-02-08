package com.rahat.blog.controllers;

import com.rahat.blog.domain.dtos.LoginRequestDto;
import com.rahat.blog.domain.dtos.LoginResponseDto;
import com.rahat.blog.domain.dtos.RegResponseDto;
import com.rahat.blog.domain.dtos.RegiRequestDto;
import com.rahat.blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegResponseDto>  registration (
            @Valid @RequestBody RegiRequestDto regiRequest){
        RegResponseDto regResponseDto =  authService.registration(regiRequest.username(), regiRequest.email(), regiRequest.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(regResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        LoginResponseDto response =
                authService.login(request.email(), request.password());

        return ResponseEntity.ok(response);
    }
}



