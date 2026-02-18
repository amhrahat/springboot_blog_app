package com.rahat.blog.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rahat.blog.domain.entities.User;
import com.rahat.blog.security.BlogUserDetail;

@Service
public class CurrentUserService {

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof BlogUserDetail blogUserDetail) {
            return blogUserDetail.getUser();
        }

        assert principal != null;
        throw new RuntimeException(
                "Unknown principal type: " + principal.getClass()
        );
    }
}
