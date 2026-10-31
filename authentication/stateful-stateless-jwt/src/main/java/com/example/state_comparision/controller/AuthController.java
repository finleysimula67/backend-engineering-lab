package com.example.state_comparision.controller;

import com.example.state_comparision.security.JwtService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public String generateToken(@RequestParam String username) {
        return jwtService.generateToken(username);
    }


@GetMapping("/validate")
    public String validateToken(
            @RequestParam String token,
            @RequestParam String username) {

        boolean valid = jwtService.isTokenValid(token, username);

        return valid
                ? "JWT is valid!"
                : "JWT is invalid!";
    }

}
