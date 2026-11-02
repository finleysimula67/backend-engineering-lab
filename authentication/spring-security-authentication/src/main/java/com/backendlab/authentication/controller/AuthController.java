package com.backendlab.authentication.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendlab.authentication.dto.LoginRequest;
import com.backendlab.authentication.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String username = authenticationService.authenticate(
                request.username(),
                request.password()
        );

        return Map.of(
                "message", "Authentication successful",
                "username", username
        );
    }
}
