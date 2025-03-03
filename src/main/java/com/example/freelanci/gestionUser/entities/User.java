package com.example.freelanci.gestionUser.entities;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String profileDescription;

    private double accountBalance;
    private String phoneNumber;
    private String address;
    private boolean isEmailVerified;
    private boolean isProfileCompleted;

    private String profilePictureUrl;

    private String location;

    @Enumerated(EnumType.STRING)
    private UserRole role; // Un seul rôle pour l'utilisateur

    private UserStatus status; // Enum pour le statut de l'utilisateur

    @Column(nullable = false, columnDefinition = "int default 0")
    private int failedLoginAttempts;  // Nombre de tentatives échouées

    // Enum pour les rôles des utilisateurs
    public enum UserRole {
        ADMIN,
        CLIENT,
        FREELANCER
    }

    // Enum pour le statut de l'utilisateur
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED,
        SUSPENDED
    }

}
