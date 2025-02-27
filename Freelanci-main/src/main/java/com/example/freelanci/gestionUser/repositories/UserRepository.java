package com.example.freelanci.gestionUser.repositories;

import com.example.freelanci.gestionUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom queries can be added here if needed
    User findByUsername(String username);
    User findByEmail(String email);
}
