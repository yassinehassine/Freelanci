package com.example.freelanci.gestionUser.services;

import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create or Update a user (CRUD - Create and Update)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Read - Get a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Read - Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete - Remove a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Custom method to find by username (Example)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Custom method to find by email (Example)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
