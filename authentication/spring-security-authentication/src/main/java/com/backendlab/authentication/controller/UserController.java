package com.backendlab.authentication.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api")
public class UserController {

    @GetMapping("/users/me")
    public String getCurrentUser(Authentication authentication )
    {
        return "Authenticated user: " + authentication.getName();
    }

}
