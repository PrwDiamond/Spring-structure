package com.example.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    private SecurityUtil() {

    }

    public static Optional<String> getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }

        Object principle = authentication.getPrincipal();
        if (principle == null) {
            return Optional.empty();
        }

        String userId = (String) principle;
        return Optional.of(userId);
    }

}
