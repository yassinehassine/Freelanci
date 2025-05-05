package com.example.freelanci.gestionClient.repositories;

import com.example.freelanci.gestionClient.dto.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.freelanci.gestionClient.entities.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Custom queries can be added here, for example:
    // List<Review> findByJobId(Long jobId);
    @Query("SELECT new com.example.freelanci.gestionClient.dto.ReviewDTO(r.reviewId, r.rating, r.comment, r.job.jobId) " +
            "FROM Review r WHERE r.job.jobId = :jobId")
    List<ReviewDTO> findAllByJobId(@Param("jobId") Long jobId);
}
