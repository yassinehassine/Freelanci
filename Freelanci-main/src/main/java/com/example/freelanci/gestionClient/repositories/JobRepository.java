package com.example.freelanci.gestionClient.repositories;

import com.example.freelanci.gestionClient.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepository extends JpaRepository<Job, Long> {
    // You can add custom queries here if needed, e.g., search jobs by category or budget.
}
