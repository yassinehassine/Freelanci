package com.example.freelanci.gestionUser.entities;

import com.example.freelanci.gestionFreelancer.entities.Proposal;
import com.example.freelanci.gestionFreelancer.entities.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private UserRole role; // Integrated role field as Enum

    private UserStatus status; // Enum for User status

    // Enum for User Roles
    public enum UserRole {
        ADMIN,
        CLIENT,
        FREELANCER
    }

    // Enum for User Status
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED,
        SUSPENDED
    }

    @OneToMany(mappedBy="freelancer")
    private Set<Proposal> Proposals;
    @OneToMany(mappedBy="freelancer")
    private Set<Work> Works;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public boolean isProfileCompleted() {
        return isProfileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        isProfileCompleted = profileCompleted;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Proposal> getProposals() {
        return Proposals;
    }

    public void setProposals(Set<Proposal> proposals) {
        Proposals = proposals;
    }

    public Set<Work> getWorks() {
        return Works;
    }

    public void setWorks(Set<Work> works) {
        Works = works;
    }
}
