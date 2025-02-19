package com.example.freelanci.gestionClient.repositories;

import com.example.freelanci.gestionClient.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.freelanci.gestionClient.entities.Review;
import java.util.List;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByJob(Job job);
}
