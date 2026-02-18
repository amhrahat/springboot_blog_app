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
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof BlogUserDetail blogUserDetail) {
            return blogUserDetail.getUser();
        }

        return null;
    }
}
