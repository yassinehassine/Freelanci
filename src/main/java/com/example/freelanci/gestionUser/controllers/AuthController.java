package com.example.freelanci.gestionUser.controllers;

import com.example.freelanci.gestionUser.services.AuthService;
import jakarta.persistence.Column;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (authService.authenticate(username, password)) {
            return "Login successful!";
        }
        return "Invalid username or password.";
    }
}

