package com.example.freelanci.gestionUser.repositories;

import com.example.freelanci.gestionUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom queries can be added here if needed
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    @Query("SELECT u.location, COUNT(u) FROM User u GROUP BY u.location ORDER BY COUNT(u) DESC")
    List<Object[]> countUsersByLocation();
    List<User> findByRole(User.UserRole role);
}
