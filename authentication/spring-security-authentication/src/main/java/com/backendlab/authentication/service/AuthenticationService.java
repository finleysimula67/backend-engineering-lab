package com.backendlab.authentication.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(String username, String password) {

        System.out.println("Login attempt for: " + username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        Authentication result = authenticationManager.authenticate(authentication);

        System.out.println("Authentication successful: " + result.isAuthenticated());

        return result.getName();
    }
}