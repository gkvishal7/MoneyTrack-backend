package com.vishal.MoneyTrack.config;

import com.vishal.MoneyTrack.exceptions.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    /**
     * Get the current user ID from the SecurityContext.
     * The JwtFilter sets the user ID as the principal/name in the authentication token.
     *
     * @return UUID of the current user
     * @throws AuthenticationException if no user is authenticated
     */
    public static UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null || auth.getName() == null || auth.getName().equals("anonymousUser")) {
            throw new AuthenticationException("User not authenticated");
        }
        try {
            return UUID.fromString(auth.getName());
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Invalid user ID format in security context");
        }
    }
}
