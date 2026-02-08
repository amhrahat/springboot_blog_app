package com.rahat.blog.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {

    private final HttpServletRequest request;

    public CsrfTokenController(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken() {
        return (CsrfToken)this.request.getAttribute("_csrf");
    }
}
