package com.connectbundle.connect.service;

import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.exception.UnauthorizedException;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized access");
        }

        String username = authentication.getName(); // Fetch authenticated user's username
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
    }
}
