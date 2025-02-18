package com.example.freelanci.gestionClient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.freelanci.gestionClient.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Custom queries can be added here, for example:
    // List<Review> findByJobId(Long jobId);
}
