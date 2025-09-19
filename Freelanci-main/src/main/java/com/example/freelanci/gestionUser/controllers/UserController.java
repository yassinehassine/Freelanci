package com.example.freelanci.gestionUser.controllers;

import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    // Create or Update User (POST/PUT)
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        try {
            User savedUser = userService.saveUser(user);
            response.put("success", true);
            response.put("user", savedUser);
            response.put("message", messageSource.getMessage("user.created.success", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", messageSource.getMessage("user.creation.failed", null, currentLocale));
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Get User by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            response.put("success", true);
            response.put("user", user.get());
            response.put("message", messageSource.getMessage("user.found.success", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", messageSource.getMessage("user.not.found", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get all users (GET)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        List<User> users = userService.getAllUsers();
        response.put("success", true);
        response.put("users", users);
        response.put("count", users.size());
        response.put("message", messageSource.getMessage("user.found.success", null, currentLocale));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete User by ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            response.put("success", true);
            response.put("message", messageSource.getMessage("user.deleted.success", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", messageSource.getMessage("user.not.found", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update User (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            try {
                user.setId(id); // Ensure the ID remains the same
                User updatedUser = userService.saveUser(user);
                response.put("success", true);
                response.put("user", updatedUser);
                response.put("message", messageSource.getMessage("user.updated.success", null, currentLocale));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("success", false);
                response.put("message", messageSource.getMessage("user.update.failed", null, currentLocale));
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.put("success", false);
            response.put("message", messageSource.getMessage("user.not.found", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get user by username (GET)
    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        User user = userService.getUserByUsername(username);
        if (user != null) {
            response.put("success", true);
            response.put("user", user);
            response.put("message", messageSource.getMessage("user.found.success", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", messageSource.getMessage("user.not.found", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get user by email (GET)
    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        User user = userService.getUserByEmail(email);
        if (user != null) {
            response.put("success", true);
            response.put("user", user);
            response.put("message", messageSource.getMessage("user.found.success", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", messageSource.getMessage("user.not.found", null, currentLocale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
