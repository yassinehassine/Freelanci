package com.example.freelanci.gestionUser.services;

import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.isPresent() && userOpt.get().getPassword().equals(password);
    }
}